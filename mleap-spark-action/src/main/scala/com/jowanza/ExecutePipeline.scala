package com.jowanza

import ml.combust.bundle.BundleFile
import ml.combust.mleap.runtime.MleapSupport._
import ml.combust.mleap.runtime.serialization.FrameReader
import resource._

object ExecutePipeline extends App {

  // load the Spark pipeline we saved in the previous section
  val bundle = (for(bundleFile <- managed(BundleFile("jar:file:/Users/jowanzajoseph/Downloads/airbnb.model.lr.zip"))) yield {
    bundleFile.loadMleapBundle().get
  }).opt.get

  val s = scala.io.Source.fromURL("file:///Users/jowanzajoseph/Downloads/frame.airbnb.json").mkString
  val bytes = s.getBytes("UTF-8")

  val g = FrameReader("ml.combust.mleap.json").fromBytes(bytes).get

  g.show()

  val mleapPipeline = bundle.root

  print(mleapPipeline.transform(g).get.dataset.last.last)
  

}
