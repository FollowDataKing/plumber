package org.plumber.api

import com.typesafe.config.Config
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.DStream

/**
 * Created by baihe on 16/4/11.
 */
abstract class Outlet[T](conf: Config) {
  /**
   * The interface method to *push* a DStream of specified type to the outlet
   *
   * @param ssc the Spark StreamingContext
   * @param dStream the output stream
   */
  def push(ssc: StreamingContext, dStream: DStream[T]) : Unit
}
