package org.plumber.core

import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.DStream

import scala.reflect.runtime.universe._

/**
 * Created by baihe on 16/4/11.
 */
trait Outlet {
  def push[T](params: String*)(implicit tt: TypeTag[T]) : (StreamingContext, DStream[T]) => Unit
}
