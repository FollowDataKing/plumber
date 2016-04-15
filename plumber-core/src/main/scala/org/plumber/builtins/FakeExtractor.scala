package org.plumber.builtins

import com.typesafe.config.Config
import org.apache.spark.streaming.dstream.DStream
import org.plumber.api.{Drip, Extractor}

/**
 * Created by baihe on 16/4/13.
 */
class FakeExtractor(conf: Config) extends Extractor[Any](conf) {
  override def transform(stream: DStream[Any]): DStream[Drip[_]] = stream.map(Drip(_))
//  override def transform(stream: DStream[Any]): DStream[Drip] = stream.map(Drip(_))
}
