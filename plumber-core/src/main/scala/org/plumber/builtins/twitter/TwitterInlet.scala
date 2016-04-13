package org.plumber.builtins.twitter

import com.typesafe.config.Config
import org.apache.spark.Logging
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.twitter.TwitterUtils
import org.plumber.api.Inlet
import twitter4j.Status

import scala.collection.JavaConverters._

/**
 * Created by baihe on 16/4/12.
 */
class TwitterInlet(conf: Config) extends Inlet[Status](conf) with Logging {
  override def pull(ssc: StreamingContext) : DStream[Status] = {

    val filters = conf.getStringList(TwitterInlet.CONF_TWITTER_FILTERS).asScala.toSeq

    TwitterUtils.createStream(ssc, None, filters)
  }
}

object TwitterInlet {
  final val CONF_TWITTER = "twitter"

  final val CONF_TWITTER_FILTERS = CONF_TWITTER + ".filters"
}
