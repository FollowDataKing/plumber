package org.plumber.api

import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.DStream
import org.plumber.PlumberContext

import scala.reflect.runtime.universe._

/**
 * Created by baihe on 16/4/11.
 */
abstract class Outlet[T] {
  def push(plumberContext: PlumberContext, dStream: DStream[T]) : Unit
}
