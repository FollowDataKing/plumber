package org.plumber.conf

import java.io.File

import com.typesafe.config.{ConfigValueFactory, Config, ConfigFactory}
import org.apache.spark.Logging

/**
 * A typesafe config object to customize the plumber job
 *
 * Created by baihe on 16/4/11.
 */
class PlumberConf (confOpt: Option[Config]) extends Cloneable with Logging {

  // The inner typesafe config object, with fallback to default config
  private val plumberConf = (if (confOpt.isDefined) ConfigFactory.load(confOpt.get)
    else ConfigFactory.empty()).withFallback(PlumberConf.defaultConf)

  /**
   * The default constructor, with default configs
   * @return the default plumber config
   */
  def this() = this(None)

  /**
   * Enrich with customized user config file
   * @param confPath the config file path
   * @return the customized plumber config
   */
  def load(confPath: String): PlumberConf = {
    val confFile = new File(confPath)

    require(confFile.exists())

    return new PlumberConf(Option(ConfigFactory.parseFile(confFile).withFallback(plumberConf)))
  }

  /**
   * Set a configuration variable.
   * @param key the given key
   * @param value the config value
   * @return cloned plumber config with updates
   */
  def set(key: String, value: String): PlumberConf = {
    if (key == null) {
      throw new NullPointerException("null key")
    }
    if (value == null) {
      throw new NullPointerException("null value for " + key)
    }

    return new PlumberConf(Option(plumberConf.withValue(key, ConfigValueFactory.fromAnyRef(value))))
  }
}

object PlumberConf {
  val defaultConf = ConfigFactory.load("plumber.default")

  def init() = new PlumberConf()

  /**
   * The implicit conversion function to implement the delegate pattern
   * from PlumberConf to inner typeSafe config object
   * @param plumberConf the PlumberConf object
   * @return the converted inner typesafe config object
   */
  implicit def PlumberConf2TypeSafe(plumberConf: PlumberConf) = plumberConf.plumberConf

  final val ROOT: String = "plumber"

  final val INLET: String = ROOT + ".inlet"
  final val INLET_CLASS: String = INLET + ".class"

  final val OUTLET: String = ROOT + ".outlet"
  final val OUTLET_CLASS: String = OUTLET + ".class"
}

