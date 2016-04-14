package org.plumber.builtins

import com.typesafe.config.Config
import org.apache.spark.streaming.dstream.DStream
import org.plumber.api.Publisher

/**
  * Created by baihe on 16/4/13.
  */
class FakePublisher(conf: Config) extends Publisher[Any](conf) {
  override def transform(stream: DStream[_]): DStream[Any] = stream.asInstanceOf[DStream[Any]]
}
