package org.plumber.api

import com.typesafe.config.Config
import org.apache.spark.streaming.dstream.DStream

/**
 * Created by baihe on 16/4/11.
 */
abstract class Valve(conf: Config) {
  def convert(source: DStream[Map[String, Any]]): DStream[Map[String, Any]]
}
