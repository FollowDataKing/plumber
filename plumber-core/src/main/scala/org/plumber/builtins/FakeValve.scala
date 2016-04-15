package org.plumber.builtins

import org.apache.spark.streaming.dstream.DStream
import org.plumber.api.{Drip, Valve}

import com.typesafe.config.Config
/**
 * Created by baihe on 16/4/14.
 */
class FakeValve(conf: Config) extends Valve(conf) {
  override def convert(source: DStream[Drip[_]]): DStream[Drip[_]] = source
//  override def convert(source: DStream[Drip]): DStream[Drip] = source
}
