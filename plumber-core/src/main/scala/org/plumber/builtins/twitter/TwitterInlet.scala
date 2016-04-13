package org.plumber.builtins.twitter

import org.apache.spark.Logging
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.twitter.TwitterUtils
import org.plumber.PlumberContext
import org.plumber.api.Inlet
import org.plumber.conf.PlumberConf
import twitter4j.Status

import scala.collection.JavaConverters._

/**
 * Created by baihe on 16/4/12.
 */
class TwitterInlet(context: PlumberContext) extends Inlet[Status](context) with Logging {
  override def pull(ssc: StreamingContext) : DStream[Status] = {
    val plumberConf = context.plumberConf

    val filters = plumberConf.getStringList(TwitterInlet.INLET_TWITTER_FILTERS).asScala.toSeq

    TwitterUtils.createStream(ssc, None, filters)
  }
}

object TwitterInlet {
  final val INLET_TWITTER = PlumberConf.INLET + ".twitter"

  final val INLET_TWITTER_FILTERS = INLET_TWITTER + ".filters"
}
