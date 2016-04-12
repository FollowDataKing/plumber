package org.plumber.core.builtins.kafka

import kafka.serializer.{DefaultDecoder, StringDecoder}
import org.apache.spark.Logging
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.kafka.KafkaUtils
import org.plumber.core.Inlet

import scala.reflect.runtime.universe._

/**
 * Created by baihe on 16/4/12.
 */
/**
 * Created by baihe on 16/2/29.
 *
 * Kafka数据源,基于Generic类型区分如下类型
 *
 * case 1. 无header的纯文本流
 * case 2. 无header的二进制流
 * case 3. 有header的纯文本流
 * case 4. 有header的二进制流
 */
object KafkaInlet extends Inlet with Logging {
  override def pull[S](params: String*)(implicit st: TypeTag[S])
  : StreamingContext => DStream[S] = {

    require(params != null && params.length >= 2)

    (ssc: StreamingContext) => {

      val brokers = params(0)
      val topics = params(1)

      val topicsSet = topics.split(",").toSet
      val kafkaParams = Map[String, String]("metadata.broker.list" -> brokers)

      typeOf[S] match {
        // case 1. 无header的纯文本流
        case t if t =:= typeOf[String] =>
          logDebug("Kafka source containing *headless string* stream")
          KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
            ssc, kafkaParams, topicsSet).map(_._2)
        // case 2. 无header的二进制流
        case t if t =:= typeOf[Array[Byte]] =>
          logDebug("Kafka source containing *headless byte-array* stream")
          KafkaUtils.createDirectStream[String, Array[Byte], StringDecoder, DefaultDecoder](
            ssc, kafkaParams, topicsSet).map(_._2)
        // case 3. 有header的纯文本流
        case t if t =:= typeOf[(String, String)] =>
          logDebug("Kafka source containing *with-head string* stream")
          KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
            ssc, kafkaParams, topicsSet)
        // case 4. 有header的二进制流
        case t if t =:= typeOf[(String, Array[Byte])] =>
          logDebug("Kafka source containing *with-head byte-array* stream")
          KafkaUtils.createDirectStream[String, Array[Byte], StringDecoder, DefaultDecoder](
            ssc, kafkaParams, topicsSet)
        case _ => {
          logError("Kafka source containing *non-supported* stream")
          throw new UnsupportedOperationException
        }
      }
    }.asInstanceOf[DStream[S]]
  }

}
