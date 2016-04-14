package org.plumber

import com.typesafe.config.Config
import org.apache.spark.Logging
import org.plumber.api._
import org.plumber.conf.PlumberConf
import org.plumber.conf.PlumberConf._

import scala.collection.JavaConverters._

/**
 * Created by baihe on 16/4/12.
 */
object PlumberTask extends Logging {

  /**
   * Assemble the trunk pipe
   * @param conf
   * @return
   */
  def assembleTrunk(conf: PlumberConf): Trunk = {
    val trunkConf = conf.getConfig(PlumberConf.CONF_TRUNK)

    val inlet = getInlet(trunkConf.getConfig("inlet"))
    val rectifier = getExtractor(trunkConf.getConfig("rectifier"))
    val valves = trunkConf.getConfigList("valves").asScala.toIterable.map(getValve)

    Trunk(inlet, rectifier, valves)
  }

  /**
   * Assemble all branch pipes
   * @param conf
   * @return
   */
  def assembleBranches(conf: PlumberConf): Iterable[Branch] = {
    conf.getConfigList(PlumberConf.CONF_BRANCHES).asScala.toIterable.map(assembleBranch)
  }

  /**
   * Assemble the branch pipe
   * @param conf
   * @return
   */
  private def assembleBranch(conf: Config) = {
    val outlet = getOutlet(conf.getConfig("outlet"))
    val deRectifier = getPublisher(conf.getConfig("derectifier"))
    val valves = conf.getConfigList("valves").asScala.toIterable.map(getValve)

    Branch(valves, deRectifier, outlet)
  }

  private def getInlet(conf: Config) : Inlet[Any] = {
    val inletClass = conf.getString("class")
    val inletInst = Class.forName(inletClass).getConstructor(classOf[Config]).newInstance(conf)
      .asInstanceOf[Inlet[Any]]
    inletInst
  }

  private def getExtractor(conf: Config) : Extractor[Any] = {
    val rectClass = conf.getString("class")
    val rectInst = Class.forName(rectClass).getConstructor(classOf[Config]).newInstance(conf)
      .asInstanceOf[Extractor[Any]]
    rectInst
  }

  private def getValve(conf: Config) = {
    val valveClass = conf.getString("class")
    val valveInst = Class.forName(valveClass).getConstructor(classOf[Config]).newInstance(conf)
      .asInstanceOf[Valve]
    valveInst
  }

  private def getPublisher(conf: Config) : Publisher[Any] = {
    val derectClass = conf.getString("class")
    val derectInst = Class.forName(derectClass).getConstructor(classOf[Config]).newInstance(conf)
      .asInstanceOf[Publisher[Any]]
    derectInst
  }

  private def getOutlet(conf: Config) : Outlet[Any] = {
    val outletClass = conf.getString("class")
    val outletInst = Class.forName(outletClass).getConstructor(classOf[Config]).newInstance(conf)
      .asInstanceOf[Outlet[Any]]
    outletInst
  }
}

