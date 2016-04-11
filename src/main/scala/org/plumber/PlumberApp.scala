package org.plumber

import java.io.File

import com.typesafe.config.ConfigFactory

/**
 * Created by baihe on 16/4/11.
 */
object PlumberApp extends App {

  val plumberConf = ConfigFactory.parseFile(new File("/Users/admin/fuck.conf"))

  val projName = plumberConf.getString("org.plumber.aa")

  println(projName)

}
