name := "Spark"

version := "0.1"

scalaVersion := "2.12.10"

// https://mvnrepository.com/artifact/org.apache.spark/spark-core
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "3.0.3",
  "org.apache.spark" %% "spark-sql" % "3.0.3",
  "org.apache.thrift" % "libthrift" % "0.14.2"
)

//assemblyMergeStrategy / assembly := {
assembly / assemblyMergeStrategy := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}


