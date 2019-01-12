package com.jowanza

import ml.combust.mleap.runtime.frame.DefaultLeapFrame
import ml.combust.mleap.runtime.frame.ArrayRow
import ml.combust.bundle.BundleFile
import ml.combust.mleap.runtime.MleapSupport._
import resource._
import spray.json._
import ml.combust.mleap.core.types._


object ExecutePipeline {
  /**
    * Process the JSON Array of Airbnb Values
    * @param values<Seq<Any>>
    * @return
    */
  def predictModel(values: Seq[Any]): String = {
    // Grab mleap bundle from the file system
    val bundle = (for (bundleFile <- managed(BundleFile("jar:file:///action/mleap-spark-action/airbnb.model.lr.zip"))) yield {
      bundleFile.loadMleapBundle().get
    }).opt.get


    val schema: StructType = StructType(StructField("state", ScalarType.String),
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

    val pipeLine = bundle.root
    val rowValues = ArrayRow.apply(values)
    val data = DefaultLeapFrame(schema, Seq(rowValues))

    val predict = pipeLine.transform(data).get.dataset.last.last

    object MyJsonProtocol extends DefaultJsonProtocol {
      implicit val predictionFormat = jsonFormat1(Prediction)
    }
    import MyJsonProtocol._
    // Return a JSON String of the Predicted Value
    Prediction(predict.asInstanceOf[Double]).toJson.toString()
  }
}



case class Prediction(price: Double)