package org.plumber.builtins.kafka

import kafka.serializer.{DefaultDecoder, StringDecoder}
import org.apache.spark.Logging
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.kafka.KafkaUtils
import org.plumber.PlumberContext
import org.plumber.PlumberContext._
import org.plumber.api.Inlet

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
object KafkaInlet extends Inlet[(String, Array[Byte])] with Logging {
  /**
    * The interface method to *pull* a DStream of specified type from the inlet
    *
    * @return the source DStream
    */
  override def pull(plumberContext: PlumberContext): DStream[(String, Array[Byte])] = {
    val plumberConf = plumberContext.plumberConf

    val brokers = plumberConf.getString("org.plumber.kafka.brokers")
    val topics = plumberConf.getString("org.plumber.kafka.topics")

    val topicsSet = topics.split(",").toSet
    val kafkaParams = Map[String, String]("metadata.broker.list" -> brokers)

    KafkaUtils.createDirectStream[String, Array[Byte], StringDecoder, DefaultDecoder](
        plumberContext, kafkaParams, topicsSet)
  }
}
