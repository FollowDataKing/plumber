/**
 * Created by baihe on 16/4/8.
 */

import sbt._

object Dependencies {
  // Versions
  lazy val sparkVersion = "1.6.0"
  lazy val hbaseVersion = "0.98.11-hadoop2"

  lazy val tsConfigVersion = "1.2.1"

  val sparkCore = "org.apache.spark" %% "spark-core" % sparkVersion
  val sparkSql = "org.apache.spark" %% "spark-sql" % sparkVersion
  val sparkStreaming = "org.apache.spark" %% "spark-streaming" % sparkVersion
  val sparkKafka = "org.apache.spark" %% "spark-streaming-kafka" % sparkVersion
  val sparkTwitter = "org.apache.spark" %% "spark-streaming-twitter" % sparkVersion
  val sparkHive = "org.apache.spark" % "spark-hive_2.10" % sparkVersion

  val tsConfig = "com.typesafe" % "config" % tsConfigVersion

  val confDependencies = Seq(
    sparkCore
  )

  val runtimeDependencies = Seq(

  )

  val coreDependencies = Seq(
    sparkStreaming % Provided,
    sparkHive % Provided,
    sparkTwitter % Provided,
    sparkKafka % Provided
  )

  var plumberDependencies = Seq(
    sparkStreaming % Provided
  )
}
