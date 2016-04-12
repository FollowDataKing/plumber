package org.plumber.api

import org.apache.spark.streaming.dstream.DStream
import org.plumber.PlumberContext

import scala.reflect.runtime.universe._

/**
 * Created by baihe on 16/4/11.
 */
abstract class Inlet[S] {

  /**
   * The interface method to *pull* a DStream of specified type from the inlet
   *
   * @return the source DStream
   */
  def pull(context: PlumberContext) : DStream[S]
}

