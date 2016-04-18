package org.plumber.builtins.twitter

import com.typesafe.config.Config
import org.apache.spark.streaming.dstream.DStream
import org.plumber.api.{Drip, Extractor}
import twitter4j.Status

/**
 * Created by baihe on 16/4/18.
 */
class TwitterExtractor(conf: Config) extends Extractor[Any](conf) {
  override def transform(stream: DStream[Any]): DStream[Drip] = stream.map(_.asInstanceOf[Status]).map(status =>
    Drip(Map("timestamp" -> status.getCreatedAt.toString, "user" -> status.getUser.getName, "content" -> status.getText)))
}
