package org.plumber.runtime

import org.plumber.conf.PlumberConf
import org.plumber.core.{Valve, Outlet, Inlet}

/**
 * Created by baihe on 16/4/12.
 */
class PlumberContext(config: PlumberConf) {
  def getInlet() : Inlet = ???
  def getOutlet() : Outlet = ???
  def getValves() : Iterable[Valve] = ???
}
