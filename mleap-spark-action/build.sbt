name := "openwhisk_ibm_cloudant"

version := "1.0"

scalaVersion := "2.11.6"

assemblyJarName := "chat.jar"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "com.cloudant" % "cloudant-client" % "2.6.2"

libraryDependencies += "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.6.1"

libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % "2.1.2"

//libraryDependencies += "ml.combust.mleap" %% "mleap-spark" % "0.10.0"

//libraryDependencies += "ml.combust.mleap" %% "mleap-runtime" % "0.10.0"

// for debugging sbt problems
logLevel := Level.Debug