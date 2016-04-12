package org.plumber.core

import org.apache.spark.sql.types.StructType
import org.apache.spark.streaming.dstream.DStream

/**
 * Created by baihe on 16/4/11.
 */
trait Valve {
  // required: source scheme

  val sourceSchema: StructType
  // optional: target scheme

  def convert(source: DStream[Record])
}
