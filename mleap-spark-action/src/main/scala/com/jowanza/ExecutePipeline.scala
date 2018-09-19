package com.jowanza

import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import ml.combust.bundle.BundleFile
import ml.combust.mleap.runtime.MleapSupport._
import ml.combust.mleap.runtime.serialization.FrameReader
import resource._

object ExecutePipeline {

  // load the Spark pipeline we saved in the previous section


//  val s = scala.io.Source.fromURL("file:///Users/jowanzajoseph/Downloads/frame.airbnb.json").mkString
//  val bytes = s.getBytes("UTF-8")

  implicit def getObjectMapper(): ObjectMapper = {
    val objectMapper = new ObjectMapper()
    objectMapper.registerModule(DefaultScalaModule)
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
  }



  def predictModel(values: String): String ={
    val bundle = (for(bundleFile <- managed(BundleFile("jar:file:/action/mleap-spark-action/airbnb.model.lr.zip"))) yield {
      bundleFile.loadMleapBundle().get
    }).opt.get

    val data = values.getBytes("UTF-8")
    val g = FrameReader("ml.combust.mleap.json").fromBytes(data).get

    val mleapPipeline = bundle.root
    val predict = mleapPipeline.transform(g).get.dataset.last.last

    getObjectMapper().writeValueAsString(Prediction(predict.asInstanceOf[Double]))
  }



}

case class Prediction(price: Double)