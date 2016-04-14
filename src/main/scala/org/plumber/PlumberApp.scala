package org.plumber

import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}
import org.plumber.conf.PlumberConf


/**
 * Created by baihe on 16/4/11.
 */
object PlumberApp {

  def main(args: Array[String]): Unit = {
    val CONF_ARG = 0

    val conf = if (args.length > 0) {
      val confPath = args(CONF_ARG)
      PlumberConf.init().load(confPath)
    } else {
      println("PlumberApp <config_path>")
      println("Missing the parameter <config_path>, use the default config ...")
      PlumberConf.init()
    }

    val taskName = conf.getString("plumber.name")

    val batchTime = conf.getInt("plumber.spark.streaming.batchtime")
    val checkpoint = Some(conf.getString("plumber.spark.streaming.checkpoint")) getOrElse taskName

    val ssc = StreamingContext.getOrCreate(checkpoint,
      () => {
        // Note: we should not build a stream out of a context retrieved from checkpoint
        // since it already contains all necessary elements
        createContext(batchTime, checkpoint, conf)
      })

    ssc.start()
    ssc.awaitTermination()
  }

  /**
   * The inner work logic to process all the stream
   * @param batchTime the batch time option of the plumber work
   * @param checkpoint the checkpoint path option
   * @return the new created streaming context
   */
  private final def createContext(batchTime: Int, checkpoint: String, conf: PlumberConf) : StreamingContext = {

    // The spark context object
    val sc: SparkContext = new SparkContext(new SparkConf())

    val ssc = new StreamingContext(sc, Seconds(batchTime))
    ssc.checkpoint(checkpoint)

    // 0. At first the plumber assemble the pipework, including the trunk and all the branches
    val trunk = PlumberTask.assembleTrunk(conf)
    val branches = PlumberTask.assembleBranches(conf)

    // Then let it flow ...

    // 1. Pull the source stream from the trunk inlet
    val sourceStream: DStream[Any] = trunk.inlet.pull(ssc)

    // 2. Trunk stream flows through all the valves in trunk
    var trunkStream: DStream[_] = trunk.rectifier.transform(sourceStream)
    for (valve <- trunk.valves) {
      trunkStream = valve.convert(trunkStream)
    }

    val toForkStream: DStream[_]  = trunkStream

    // 3. Fork the trunk stream to different branches
    for (branch <- branches) {

      var branchStream: DStream[_] = toForkStream

      // 3.1. Branch stream flows through all the valves in this branch
      for (valve <- branch.valves) {
        branchStream = valve.convert(branchStream)
      }

      val toPushStream = branch.deRectifier.transform(branchStream)

      branch.outlet.push(ssc, toPushStream)
    }

    ssc
  }
}
