package org.plumber.api

import com.typesafe.config.Config
import org.apache.spark.streaming.dstream.DStream

/**
 * Created by baihe on 16/4/13.
 */
abstract class Rectifier[S](conf: Config) {

  def transform(stream: DStream[S]): DStream[Map[String, Any]]
}
