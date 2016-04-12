package org.plumber

import org.plumber.conf.PlumberConf


/**
 * Created by baihe on 16/4/11.
 */
object PlumberApp extends App {

  val confVal = PlumberConf.init().getString("org.plumber.aa")
  val confDefaultVal = PlumberConf.init().load("/Users/admin/fuck.conf").getString("org.plumber.aa")

  println(confVal)
  println(confDefaultVal)

}
