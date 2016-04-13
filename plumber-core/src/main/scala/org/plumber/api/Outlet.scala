package org.plumber.api

import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.DStream
import org.plumber.PlumberContext

/**
 * Created by baihe on 16/4/11.
 */
abstract class Outlet[T](context: PlumberContext) {
  /**
   * The interface method to *push* a DStream of specified type to the outlet
   *
   * @param ssc the Spark StreamingContext
   * @param dStream the output stream
   */
  def push(ssc: StreamingContext, dStream: DStream[T]) : Unit
}
