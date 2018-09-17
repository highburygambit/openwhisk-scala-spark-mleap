//package com.jowanza
//
//import ml.combust.bundle.BundleFile
//import ml.combust.mleap.runtime.MleapSupport._
//import resource._
//
//object ExecutePipeline extends App {
//
//  // load the Spark pipeline we saved in the previous section
//  val bundle = (for(bundleFile <- managed(BundleFile("jar:file:/Users/jowanzajoseph/Downloads/simple-spark-pipeline.zip"))) yield {
//    bundleFile.loadMleapBundle().get
//  }).opt.get
//
//  // create a simple LeapFrame to transform
//  import ml.combust.mleap.runtime.frame.{DefaultLeapFrame, Row}
//  import ml.combust.mleap.core.types._
//
//  // MLeap makes extensive use of monadic types like Try
//  val schema = StructType(StructField("test_string", ScalarType.String),
//    StructField("test_double", ScalarType.Double)).get
//  val data = Seq(Row("hello", 0.6), Row("MLeap", 0.2))
//  val frame = DefaultLeapFrame(schema, data)
//
//  // transform the dataframe using our pipeline
//  val mleapPipeline = bundle.root
//  val frame2 = mleapPipeline.transform(frame).get
//  val data2 = frame2.dataset
//
//  // get data from the transformed rows and make some assertions
//
//  System.out.print(data2)
//
//  assert(data2(0).getDouble(2) == 1.0) // string indexer output
//  assert(data2(0).getDouble(3) == 1.0) // binarizer output
//
//  // the second row
//  assert(data2(1).getDouble(2) == 2.0)
//  assert(data2(1).getDouble(3) == 0.0)
//
//}
