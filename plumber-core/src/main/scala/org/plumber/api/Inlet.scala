package org.plumber.api

import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.DStream
import org.plumber.PlumberContext

/**
 * Created by baihe on 16/4/11.
 */
abstract class Inlet[S](context: PlumberContext) {

  /**
   * The interface method to *pull* a DStream of specified type from the inlet
   *
   * @param ssc the Spark StreamingContext
   * @return the source stream
   */
  def pull(ssc: StreamingContext) : DStream[S]
}


