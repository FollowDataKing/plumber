package org.plumber

import org.apache.spark.Logging
import org.plumber.api.{Inlet, Outlet}
import org.plumber.conf.PlumberConf

/**
 * Created by baihe on 16/4/12.
 */
class PlumberContext(val plumberConf: PlumberConf) extends Logging {

  def getInlet() : Inlet[_] = {
    val inletClass = plumberConf.getString(PlumberConf.INLET_CLASS)
    val inletInst = Class.forName(inletClass).getConstructor(PlumberContext.getClass).newInstance(this)
      .asInstanceOf[Inlet[_]]
    inletInst
  }

  def getOutlet() : Outlet[_] = {
    val outletClass = plumberConf.getString(PlumberConf.OUTLET_CLASS)
    val outletInst = Class.forName(outletClass).getConstructor(PlumberContext.getClass).newInstance(this)
      .asInstanceOf[Outlet[_]]
    outletInst
  }

//  def getValves() : Iterable[Valve] = ???
}

object PlumberContext {
}
