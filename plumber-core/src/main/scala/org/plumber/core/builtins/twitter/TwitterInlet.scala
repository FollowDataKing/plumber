package org.plumber.core.builtins.twitter

import org.apache.spark.Logging
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.DStream
import org.plumber.core.Inlet
import twitter4j.Status

/**
 * Created by baihe on 16/4/12.
 */
object TwitterInlet extends Inlet[Status] with Logging {
  /**
   * The interface method to *pull* a DStream of specified type from the inlet
   * @param params
   * @return the source DStream
   */
//  override def pull(params: String*): (StreamingContext) => DStream[Status] = {
//    null
//  }
  /**
   * The interface method to *pull* a DStream of specified type from the inlet
   * @param params
   * @param st the source typetag to perform dynamic reflection
   * @return the source DStream
   */
  override def pull(params: String*)(implicit st: runtime.TypeTag[_root_.twitter4j.Status]): (_root_.org.apache.spark.streaming.StreamingContext) => _root_.org.apache.spark.streaming.dstream.DStream[_root_.twitter4j.Status] = ???
}
