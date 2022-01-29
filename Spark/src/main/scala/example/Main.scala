package example

import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}
import org.apache.log4j.Logger
import org.apache.log4j.Level
import Middleware.Client
import Middleware.Person
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions.{col, udf}

object Main {

  case class PrefixSuffixStruct(Prefix: String, Suffix: Int)

  def main(args: Array[String]): Unit = {

    val nameNodeIP = args(0);
    val path = args(1);

    Logger.getLogger("org").setLevel(Level.ERROR)
    val hdfsMaster = "hdfs://" + nameNodeIP + ":9000"
    val inputPath = "/data/" + path;
    val outputPath = "/data_result/" + path;

    val sparkSession = SparkSession.builder()
      .appName("SparkJob")
      .getOrCreate()

    val sparkSQL = sparkSession.sqlContext
    val data = sparkSQL.read.options(Map("header" -> "true", "inferSchema" -> "true")).csv(hdfsMaster + inputPath)

    //Lọc ra các dòng có Gender là Female
    val dataFilterFemale = FilterFemale(data)

    //Tạo cột mới là hậu tố trong tên, ví dụ Mary Jane 002107 sẽ có hậu tố là 002107
    val dataFilterFemale_FixNameColumnStruct = FixNameColumnStruct(dataFilterFemale)

    //GroupBy và count các hậu tố theo mod 3
    val dataFilterFemale_FixNameColumnStruct_GroupBySuffix = GroupByColumnModNumber(dataFilterFemale_FixNameColumnStruct, "Name.Suffix", 3)
    showDataAndSchema(dataFilterFemale_FixNameColumnStruct_GroupBySuffix)

    //Ghi kết quả xuống với định dạng file parquet
    dataFilterFemale_FixNameColumnStruct_GroupBySuffix.write.mode(SaveMode.Overwrite).parquet(hdfsMaster + outputPath)

    //Gọi vào api putToDataBase để put tất cả data vào Redis
    writeResultToDataBase(dataFilterFemale_FixNameColumnStruct)

    sparkSession.stop()
  }

  def FilterFemale(df: DataFrame): DataFrame = {
    return df.filter(col("Gender") === "Female")
  }

  def ChangeNameStructToPrefixAndSuffix: UserDefinedFunction =
    udf((name: String) => PrefixSuffixStruct(name.slice(0, name.length - 6), name.slice(name.length - 6, name.length).toInt))

  def FixNameColumnStruct(df: DataFrame): DataFrame = {
    return df.withColumn("Name", ChangeNameStructToPrefixAndSuffix(col("Name")))
  }

  def GroupByColumnModNumber(df: DataFrame, columnName: String, number: Int): DataFrame = {
    val modResultColumnName = "mod" + number.toString
    val groupByMod = df.groupBy(col(columnName).mod(number).as(modResultColumnName))
    val countResult = groupByMod.count()
    val sortResult = countResult.sort()
    return sortResult
  }

  def showDataAndSchema(df: DataFrame): Unit = {
    df.show()
    df.printSchema()
  }

  def writeResultToDataBase(df: DataFrame): Unit = {
    val client = new Client("server", 9090)
    val person = new Person()

    for (row <- df.rdd.collect) {
      val Id = row(0).toString
      val Name = row(1).toString
      val Gender = row(2).toString
      val Age = row(3).toString

      person.setId(Id)
      person.setName(Name)
      person.setGender(Gender)
      person.setAge(Age)

      client.putToDatabase(person)
    }
  }

}

