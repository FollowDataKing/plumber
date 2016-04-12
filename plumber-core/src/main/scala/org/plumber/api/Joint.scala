package org.plumber.api

import org.apache.spark.streaming.dstream.DStream

import scala.reflect.runtime.universe._


/**
 * Created by baihe on 16/4/11.
 */
trait Joint {
  def fork[T](params: String*)(implicit tt: TypeTag[T]) : Iterable[DStream[T]]
}
