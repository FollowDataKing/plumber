package org.plumber.builtins

import com.typesafe.config.Config
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.DStream
import org.joda.time.DateTime
import org.plumber.api.{Drip, Outlet}

/**
 * Created by baihe on 16/4/15.
 */
class AccessStatsOutlet(conf: Config) extends Outlet[Drip](conf){
  /**
   * The interface method to *push* a DStream of specified type to the outlet
   *
   * @param ssc the Spark StreamingContext
   * @param dStream the output stream
   */
//  override def push(ssc: StreamingContext, dStream: DStream[_$1]): Unit = ???

  // the target stream consumer function
  override def push(ssc: StreamingContext, dStream: DStream[Drip]) = {

    // PV stats identified by action/param/user
    val apuPVs: DStream[(String, Int)] =
      dStream.map(event => (event("action") + "@P" + event("param") + "@U" + event("userId"), 1)).reduceByKey(_ + _)
    // PV stats identified by action/param/date/user
    val adpuPVs: DStream[(String, Int)] =
      dStream.map(event => (event("action") + "@P" + event("param") + "@D" + new DateTime(event("timestamp"))
        .toLocalDate + "@U" + event("userId"), 1)).reduceByKey(_ + _)
    // PV stats identified by action/user
    val auPVs: DStream[(String, Int)] =
      dStream.map(event => (event("action") + "@U" + event("userId"), 1)).reduceByKey(_ + _)
    // PV stats identified by action/date/user
    val aduPVs: DStream[(String, Int)] =
      dStream.map(event => (event("action") + "@D" + new DateTime(event("timestamp")).toLocalDate + "@U" + event
      ("userId"), 1)).reduceByKey(_ + _)

//    new HBaseCock(ssc.sparkContext, "visit_stats", "cf", "pv").
//      saveDStreams(Seq(apuPVs, adpuPVs, auPVs, aduPVs))
  }
}
