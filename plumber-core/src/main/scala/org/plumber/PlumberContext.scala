package org.plumber

import org.apache.spark.streaming.StreamingContext
import org.apache.spark.Logging
import org.plumber.api.{Valve, Outlet, Inlet}
import org.plumber.conf.PlumberConf

/**
 * Created by baihe on 16/4/12.
 */
class PlumberContext(val plumberConf: PlumberConf)(implicit val streamingContext: StreamingContext) extends Logging {
  def getInlet() : Inlet = ???
  def getOutlet() : Outlet = ???
  def getValves() : Iterable[Valve] = ???
}

object PlumberContext {
  implicit def plumberContext2SparkContext(plumberContext: PlumberContext) = {
    plumberContext.streamingContext
  }
}
