package org.plumber.api

import com.typesafe.config.Config
import org.apache.spark.streaming.dstream.DStream

/**
 * Created by baihe on 16/4/13.
 */
abstract class Publisher[T](conf: Config) {

//  def transform(stream: DStream[Drip[_]]): DStream[T]
  def transform(stream: DStream[Drip]): DStream[T]
}
