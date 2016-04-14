package org.plumber.builtins.twitter

import com.typesafe.config.Config
import org.apache.spark.streaming.{StreamingContext, Seconds}
import org.apache.spark.streaming.dstream.DStream
import org.plumber.api.Outlet

/**
  * Created by baihe on 16/4/14.
  */
class TwitterTopTagsOutlet(conf: Config) extends Outlet[Any](conf) {
  val checkDuration = conf.getInt("duration")

  /**
    * The interface method to *push* a DStream of specified type to the outlet
    *
    * @param ssc     the Spark StreamingContext
    * @param dStream the output stream
    */
  override def push(ssc: StreamingContext, dStream: DStream[Any]): Unit = {
    val topTags = dStream.map((_, 1)).reduceByKeyAndWindow(_ + _, Seconds(checkDuration))
      .map{case (topic, count) => (count, topic)}
      .transform(_.sortByKey(false))

    // Print popular hashtags
    topTags.foreachRDD(rdd => {
      val topList = rdd.take(5)
      println((s"\nPopular topics in last $checkDuration seconds (%s total):").format(rdd.count()))
      topList.foreach{case (count, tag) => println("%s (%s tweets)".format(tag, count))}
    })
  }
}

