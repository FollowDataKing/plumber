package org.plumber.builtins

import com.typesafe.config.Config
import org.apache.spark.streaming.dstream.DStream
import org.plumber.api.Rectifier

/**
 * Created by baihe on 16/4/13.
 */
class FakeRectifier(conf: Config) extends Rectifier[Any](conf) {
  override def transform(stream: DStream[Any]): DStream[Map[String, Any]] = stream.map(rec => Map[String, Any]("_" -> rec))
}
