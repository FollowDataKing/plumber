package org.plumber.builtins.twitter

import com.typesafe.config.Config
import org.apache.spark.streaming.dstream.DStream
import org.plumber.api.{Drip, Valve}
import twitter4j.Status

/**
 * Created by baihe on 16/4/14.
 */
class TwitterTagValve(conf: Config) extends Valve(conf) {
  override def convert(source: DStream[Drip[_]]) = {
    source.map(_.raw)
      .flatMap(_.asInstanceOf[Status].getText.split(" ").filter(_.startsWith("#")))
      .map(Drip(_))
  }
//  override def convert(source: DStream[Drip]) = {
//    source.map(_.raw)
//      .flatMap(_.asInstanceOf[Status].getText.split(" ").filter(_.startsWith("#")))
//      .map(Drip(_))
//  }
}
