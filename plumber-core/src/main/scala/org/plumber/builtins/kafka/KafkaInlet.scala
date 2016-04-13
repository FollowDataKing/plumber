package org.plumber.builtins.kafka

import com.typesafe.config.Config
import kafka.serializer.{DefaultDecoder, StringDecoder}
import org.apache.spark.Logging
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.kafka.KafkaUtils
import org.plumber.api.Inlet

import scala.collection.JavaConverters._

/**
 * Created by baihe on 16/4/12.
 */
class KafkaInlet (conf: Config) extends Inlet[(String, Array[Byte])](conf) with Logging {

  /**
    * The interface method to *pull* a DStream of specified type from the inlet
    *
    * @return the source DStream
    */
  override def pull(ssc: StreamingContext): DStream[(String, Array[Byte])] = {
    val brokers = conf.getString(KafkaInlet.CONF_KAFKA_BROKERS)
    val topics = conf.getStringList(KafkaInlet.CONF_KAFKA_TOPICS).asScala.toSet

    val kafkaParams = Map[String, String]("metadata.broker.list" -> brokers)

    KafkaUtils.createDirectStream[String, Array[Byte], StringDecoder, DefaultDecoder](
        ssc, kafkaParams, topics)
  }
}

object KafkaInlet {
  final val CONF_KAFKA = "kafka"

  final val CONF_KAFKA_BROKERS = CONF_KAFKA + ".brokers"
  final val CONF_KAFKA_TOPICS = CONF_KAFKA + ".topics"
}
