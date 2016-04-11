package org.plumber.core

import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.DStream

import scala.reflect.runtime.universe._

/**
 * Created by baihe on 16/4/11.
 */
trait Inlet {
  /**
   * The interface method to *pull* a DStream of specified type from the inlet
   * @param params
   * @param st the source typetag to perform dynamic reflection
   * @tparam S the type of source DStream
   * @return the source DStream
   */
  def pull[S](params: String*)(implicit st: TypeTag[S]) : StreamingContext => DStream[S]
}

