package org.plumber.builtins.kafka

import com.typesafe.config.Config
import kafka.serializer.{DefaultDecoder, StringDecoder}
import org.apache.spark.Logging
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.kafka.KafkaUtils
import org.plumber.api.Inlet

import scala.collection.JavaConverters._

import scala.reflect.runtime.universe._

/**
 * Created by baihe on 16/4/12.
 */
class KafkaInlet (conf: Config) extends Inlet[(String, Array[Byte])](conf) with Logging {

  /**
    * The interface method to *pull* a DStream of specified type from the inlet
    *
    * @return the source DStream
    */
//  override def pull(ssc: StreamingContext): DStream[(String, Array[Byte])] = {
//    val brokers = conf.getString(KafkaInlet.CONF_KAFKA_BROKERS)
//    val topics = conf.getStringList(KafkaInlet.CONF_KAFKA_TOPICS).asScala.toSet
//
//    val kafkaParams = Map[String, String]("metadata.broker.list" -> brokers)
//
//    KafkaUtils.createDirectStream[String, Array[Byte], StringDecoder, DefaultDecoder](
//        ssc, kafkaParams, topics)
//  }

  override def pull(ssc: StreamingContext): DStream[(String, Array[Byte])] = pullWithType[(String, Array[Byte])](ssc)

  def pullWithType[S](ssc: StreamingContext)(implicit st: TypeTag[S]) : DStream[S] = {

      val brokers = conf.getString(KafkaInlet.CONF_KAFKA_BROKERS)
      val topics = conf.getStringList(KafkaInlet.CONF_KAFKA_TOPICS).asScala.toSet

      val kafkaParams = Map[String, String]("metadata.broker.list" -> brokers)

      typeOf[S] match {
        // case 1. 无header的纯文本流
        case t if t =:= typeOf[String] =>
          logDebug("Kafka source containing *headless string* stream")
          KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
            ssc, kafkaParams, topics).map(_._2)
        // case 2. 无header的二进制流
        case t if t =:= typeOf[Array[Byte]] =>
          logDebug("Kafka source containing *headless byte-array* stream")
          KafkaUtils.createDirectStream[String, Array[Byte], StringDecoder, DefaultDecoder](
            ssc, kafkaParams, topics).map(_._2)
        // case 3. 有header的纯文本流
        case t if t =:= typeOf[(String, String)] =>
          logDebug("Kafka source containing *with-head string* stream")
          KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
            ssc, kafkaParams, topics)
        // case 4. 有header的二进制流
        case t if t =:= typeOf[(String, Array[Byte])] =>
          logDebug("Kafka source containing *with-head byte-array* stream")
          KafkaUtils.createDirectStream[String, Array[Byte], StringDecoder, DefaultDecoder](
            ssc, kafkaParams, topics)
        case _ => {
          logError("Kafka source containing *non-supported* stream")
          throw new UnsupportedOperationException
        }
      }
    }.asInstanceOf[DStream[S]]
}

object KafkaInlet {
  final val CONF_KAFKA = "kafka"

  final val CONF_KAFKA_BROKERS = CONF_KAFKA + ".brokers"
  final val CONF_KAFKA_TOPICS = CONF_KAFKA + ".topics"
}
