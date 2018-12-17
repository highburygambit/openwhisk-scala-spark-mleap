package com.jowanza
import ml.combust.mleap.runtime.frame.{DefaultLeapFrame, Row}
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import ml.combust.mleap.runtime.frame.ArrayRow
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import ml.combust.bundle.BundleFile
import ml.combust.mleap.runtime.MleapSupport._
import resource._
import spray.json.JsValue
import ml.combust.mleap.core.types._

object ExecutePipeline {

  implicit def getObjectMapper(): ObjectMapper = {
    val objectMapper = new ObjectMapper()
    objectMapper.registerModule(DefaultScalaModule)
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
  }

  // jar:file:///action/mleap-spark-action/airbnb.model.lr.zip

  def predictModel(values: Seq[Any]): String ={
    val bundle = (for(bundleFile <- managed(BundleFile("jar:file:///action/mleap-spark-action/airbnb.model.lr.zip"))) yield {
      bundleFile.loadMleapBundle().get
    }).opt.get

//    val data = values.toString().getBytes("UTF-8")
//    val g = FrameReader("ml.combust.mleap.json").fromBytes(data).get

    val schema = StructType(StructField("state", ScalarType.String),
      StructField("bathrooms", ScalarType.Double),
      StructField("square_feet", ScalarType.Double),
      StructField("bedrooms", ScalarType.Double),
      StructField("security_deposit", ScalarType.Double),
      StructField("cleaning_fee", ScalarType.Double),
      StructField("extra_people", ScalarType.Double),
      StructField("number_of_reviews", ScalarType.Double),
      StructField("review_scores_rating", ScalarType.Double),
      StructField("room_type", ScalarType.String),
      StructField("host_is_superhost", ScalarType.String),
      StructField("cancellation_policy", ScalarType.String),
      StructField("instant_bookable", ScalarType.String)).get

    val mleapPipeline = bundle.root

//    val x = ArrayRow.apply(Seq("NY", 2.0, 1250.0, 3.0, 50.0, 30.0, 2.0, 56.0, 90.0, "Entire home/apt", "1.0", "strict", "1.0"))
    val x = ArrayRow.apply(values)
    val data = DefaultLeapFrame(schema, Seq(x))

    val predict = mleapPipeline.transform(data).get.dataset.last.last


    getObjectMapper().writeValueAsString(Prediction(predict.asInstanceOf[Double]))
  }


}

case class Prediction(price: Double)