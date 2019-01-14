# openwhisk-scala-spark-mleap

An Apache OpenWhisk Docker Action written in Scala. It serves a Spark MLlib Model, packaged as an Mleap bundle.

## Pre-requisites

* [Apache OpenWhisk](https://github.com/openwhisk/openwhisk)
* Java 1.8+
* scala 2.11.6+ and sbt 0.13+
* Docker / Docker Hub Account

## Run The Example

This repository contains a simple example serving up a model that predicts the price of an Airbnb.

```docker run -d -p 8080:8080 jowanza/scalatest:latest```


```curl -H "Content-Type: application/json" -d '{"value":{"data":["NY", 6.0, 1250.0, 3.0, 50.0,30.0, 2.0, 56.0, 90.0, "Entire home/apt", "1.0", "strict", "1.0"]}}' localhost:8080/run```

To deploy this to IBM Cloud please follow the directions [here](https://console.bluemix.net/docs/openwhisk/openwhisk_actions.html#creating-docker-actions)

## Write Your Own Example

To create your own Scala action there are a few steps.

1. Create a main object in `src->main->scala`

2. Import dependencies via SBT

3. Copy code from the main object implementation into an esec function located in `whisk-action`

4. Build your code with SBT `sbt assembly`

5. Build and push