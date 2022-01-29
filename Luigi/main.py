import luigi
import luigi.contrib.hdfs
import os
from datetime import date, timedelta, datetime
from luigi.contrib.spark import SparkSubmitTask
os.environ['USER'] = 'master'
class checkInput(luigi.ExternalTask):
    dateHour = luigi.DateHourParameter()
    def output(self):
        return luigi.contrib.hdfs.HdfsTarget(self.dateHour.strftime("/data/%Y/%m/%d/%H"))
    
class submit(SparkSubmitTask):
    dateHour = luigi.DateHourParameter(default=datetime.now())
    app = '/job/Spark-assembly-0.1.jar'
    deploy_mode = 'client'
    master = 'yarn'
    spark_submit = '/usr/local/spark/bin/spark-submit'
    def requires(self):
        return checkInput(self.dateHour)
    
    def output(self):
        return luigi.contrib.hdfs.HdfsTarget(self.dateHour.strftime("/data_result/%Y/%m/%d/%H"))

    def app_options(self):
        inputPath = self.dateHour.strftime("%Y/%m/%d/%H")
        return ['master', inputPath]
    


class HourlyExtract(luigi.WrapperTask):
    
    dateHour = luigi.DateHourParameter(default = datetime.now() + timedelta(hours=7))
    
    def requires(self):
        for i in range(24):
            previousHour = self.dateHour - timedelta(hours=i)
            yield submit(previousHour)

    

