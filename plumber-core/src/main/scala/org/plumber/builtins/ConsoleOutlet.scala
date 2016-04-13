package org.plumber.builtins

import com.typesafe.config.Config
import org.apache.spark.Logging
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.DStream
import org.plumber.api.Outlet

/**
 * Created by baihe on 16/4/13.
 */
class ConsoleOutlet (conf: Config) extends Outlet[Any](conf) with Logging {
  /**
   * The interface method to *push* a DStream of specified type to the outlet
   *
   * @param ssc the Spark StreamingContext
   * @param dStream the output stream
   */
  override def push(ssc: StreamingContext, dStream: DStream[Any]): Unit = {
    dStream.print()
  }
}
