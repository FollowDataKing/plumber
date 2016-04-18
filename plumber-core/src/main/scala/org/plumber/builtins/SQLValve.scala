package org.plumber.builtins

import com.typesafe.config.Config
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{Row, SQLContext}
import org.apache.spark.streaming.dstream.DStream
import org.plumber.api.{Drip, Valve}
import org.plumber.api.Drip._

import scala.collection.JavaConverters._

/**
 * Created by baihe on 16/4/11.
 */
class SQLValve(conf: Config) extends Valve(conf) {
  override def convert(upstream: DStream[Drip]): DStream[Drip] = {
    val convertSQL = conf.getString("sql")
    val schemaFields = conf.getConfigList("schema").asScala.toSeq

    var schema = new StructType()
    for (field <- schemaFields) {
      schema = schema.add(field.getString("key"), field.getString("type"))
    }

    val downstream: DStream[Drip] = upstream.transform(rdd => {
      val rowRDD = rdd.map(drip => {
        Row(schema.map(field => drip(field.name)): _*)
      })
      val sqlContext = SQLContext.getOrCreate(rdd.sparkContext)

      val sourceDF = sqlContext.createDataFrame(rowRDD, schema)

      sourceDF.registerTempTable("upstream")

      val targetDF = sqlContext.sql(convertSQL)
      val targetSchema = targetDF.schema

      sqlContext.sql(convertSQL).rdd.map(row => {
        Drip(row.getValuesMap[Any](targetSchema.fieldNames.toSeq))
      })
    })

    downstream
  }
}
