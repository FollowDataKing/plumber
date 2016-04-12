package org.plumber.builtins.twitter

import org.apache.spark.Logging
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.twitter.TwitterUtils
import org.plumber.PlumberContext
import org.plumber.PlumberContext._
import org.plumber.api.Inlet
import twitter4j.Status

/**
 * Created by baihe on 16/4/12.
 */
object TwitterInlet extends Inlet[Status] with Logging {
  override def pull(plumberContext: PlumberContext) : DStream[Status] = {
    TwitterUtils.createStream(plumberContext, None, null)
  }
}
