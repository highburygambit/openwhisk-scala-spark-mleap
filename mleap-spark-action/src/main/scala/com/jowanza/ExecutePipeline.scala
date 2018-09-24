package com.jowanza

import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import ml.combust.bundle.BundleFile
import ml.combust.mleap.runtime.MleapSupport._
import ml.combust.mleap.runtime.serialization.FrameReader
import resource._
import spray.json.JsValue

object ExecutePipeline {

  implicit def getObjectMapper(): ObjectMapper = {
    val objectMapper = new ObjectMapper()
    objectMapper.registerModule(DefaultScalaModule)
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
  }

  // jar:file:///action/mleap-spark-action/airbnb.model.lr.zip

  // Todo Do the MLEAP Conversion Manually https://github.com/combust/mleap/blob/master/mleap-runtime/src/main/scala/ml/combust/mleap/json/JsonSupport.scala
  def predictModel(values: JsValue): String ={
    val bundle = (for(bundleFile <- managed(BundleFile("jar:file:///Users/jowanzajoseph/Downloads/airbnb.model.lr.zip"))) yield {
      bundleFile.loadMleapBundle().get
    }).opt.get

    val data = values.toString().getBytes("UTF-8")
    val g = FrameReader("ml.combust.mleap.json").fromBytes(data).get

    val mleapPipeline = bundle.root
    val predict = mleapPipeline.transform(g).get.dataset.last.last

    getObjectMapper().writeValueAsString(Prediction(predict.asInstanceOf[Double]))
  }

  def convertJSONString(value: String): String = {

    val d= getObjectMapper().readValue(value,  classOf[Map[String, String]])

    getObjectMapper().writeValueAsString(d.get("data").get)
  }

}

case class Prediction(price: Double)