
# ***Data Pipeline with Luigi***

## ***INTRODUCTION***
* Project workflow: 
  * Data is created every 30 minutes in hdfs.
  * Transform by spark and sent request save data to middleware server every hour. 
  * Middleware server will save database to redis.
  * Luigi is responsible for scheduler.
  * Thrift is responsible for middleware.
* Version <table>
    <tr>
        <td>Spark</td>
        <td>3.0.3</td>
    </tr>
    <tr>
        <td>Hadoop</td>
        <td>2.2.7</td>
    </tr>
    <tr>
        <td>Python</td>
        <td>3.7</td>
    </tr> 
    <tr>
        <td>Scala</td>
        <td>2.12.10</td>
    </tr>
    <tr>
        <td>Java</td>
        <td>11</td>
    </tr>  
    <tr>
        <td>Luigi</td>
        <td>2.8.13</td>
    </tr>
    <tr>
        <td>Docker</td>
        <td>20.10.5</td>
    </tr>
   </table>


## ***INSTALLATION***

* You can install this project in [Data pipeline with Luigi](https://github.com/ThadaPhan/Data-Pipeline-with-Luigi.git)

## ***BUILD***

* Build Spark jar file before build docker image:
  * `cd Spark`
  * `sbt-assembly`
* Run move file script: `./moveFile.sh`.
* Create *hadoop-network* with command: `docker network create --driver bridge spark-network --subnet=172.16.0.0/16`.
* Then, you go into the project and run command to build the hadoop cluster: `docker-compose build`.
* Final, you run: `docker-compose up -d`.
* Check result every hour in [HDFS UI](http://localhost:50070).

