package org.plumber.builtins.twitter

import com.typesafe.config.Config
import org.apache.spark.streaming.dstream.DStream
import org.plumber.api.Valve
import twitter4j.Status

/**
 * Created by baihe on 16/4/14.
 */
class TwitterTagConverter(conf: Config) extends Valve(conf) {
  override def convert(source: DStream[Map[String, Any]]): DStream[Map[String, Any]] = {
    source.flatMap(rec => rec("_").asInstanceOf[Status].getText.split(" ").filter(_.startsWith("#"))).map(rec => Map
    ("_" -> rec))
  }
}
