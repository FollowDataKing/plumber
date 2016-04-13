package org.plumber.builtins

import com.typesafe.config.Config
import org.apache.spark.streaming.dstream.DStream
import org.plumber.api.DeRectifier

/**
  * Created by baihe on 16/4/13.
  */
class FakeDeRectifier(conf: Config) extends DeRectifier[Any](conf) {
  override def transform(stream: DStream[Map[String, Any]]): DStream[Any] = stream.map(rec => rec("_"))
}
