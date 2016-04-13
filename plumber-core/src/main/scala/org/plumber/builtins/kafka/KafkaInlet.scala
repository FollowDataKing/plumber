package org.plumber.builtins.kafka

import kafka.serializer.{DefaultDecoder, StringDecoder}
import org.apache.spark.Logging
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.kafka.KafkaUtils
import org.plumber.PlumberContext
import org.plumber.api.Inlet
import org.plumber.conf.PlumberConf

import scala.collection.JavaConverters._

/**
 * Created by baihe on 16/4/12.
 */
class KafkaInlet (context: PlumberContext) extends Inlet[(String, Array[Byte])](context) with Logging {

  /**
    * The interface method to *pull* a DStream of specified type from the inlet
    *
    * @return the source DStream
    */
  override def pull(ssc: StreamingContext): DStream[(String, Array[Byte])] = {
    val plumberConf = context.plumberConf

    val brokers = plumberConf.getString(KafkaInlet.INLET_KAFKA_BROKERS)
    val topics = plumberConf.getStringList(KafkaInlet.INLET_KAFKA_TOPICS).asScala.toSet

    val kafkaParams = Map[String, String]("metadata.broker.list" -> brokers)

    KafkaUtils.createDirectStream[String, Array[Byte], StringDecoder, DefaultDecoder](
        ssc, kafkaParams, topics)
  }
}

object KafkaInlet {
  final val INLET_KAFKA = PlumberConf.INLET + ".kafka"

  final val INLET_KAFKA_BROKERS = INLET_KAFKA + ".brokers"
  final val INLET_KAFKA_TOPICS = INLET_KAFKA + ".topics"
}
