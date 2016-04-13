package org.plumber.api

import com.typesafe.config.Config
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.DStream

/**
 * Created by baihe on 16/4/11.
 */
abstract class Inlet[S](conf: Config) {

  /**
   * The interface method to *pull* a DStream of specified type from the inlet
   *
   * @param ssc the Spark StreamingContext
   * @return the source stream
   */
  def pull(ssc: StreamingContext) : DStream[S]
}


