package com.jowanza

import ml.combust.bundle.BundleFile
import ml.combust.mleap.spark.SparkSupport._
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.bundle.SparkBundleContext
import org.apache.spark.ml.feature.{Binarizer, StringIndexer}
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import resource._

object CreatePipeLine extends App {

  val spark = SparkSession.builder().appName("test").config("spark.master", "local").getOrCreate()
  val datasetName = "./spark-demo.csv"

  val dataframe: DataFrame = spark.sqlContext.read.format("csv")
    .option("header", true)
    .load(datasetName)
    .withColumn("test_double", col("test_double").cast("double"))

  // User out-of-the-box Spark transformers like you normally would
  val stringIndexer = new StringIndexer().
    setInputCol("test_string").
    setOutputCol("test_index")

  val binarizer = new Binarizer().
    setThreshold(0.5).
    setInputCol("test_double").
    setOutputCol("test_bin")

  val pipelineEstimator = new Pipeline()
    .setStages(Array(stringIndexer, binarizer))

  val pipeline = pipelineEstimator.fit(dataframe)

  // then serialize pipeline
  val sbc = SparkBundleContext().withDataset(pipeline.transform(dataframe))
  for(bf <- managed(BundleFile("jar:file:/Users/jowanzajoseph/Downloads/simple-spark-pipeline.zip"))) {
    pipeline.writeBundle.save(bf)(sbc).get
  }


}
