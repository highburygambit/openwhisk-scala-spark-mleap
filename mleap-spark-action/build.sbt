name := "mleap-spark-action"

version := "1.0"

scalaVersion := "2.11.6"

assemblyJarName := "mleap.jar"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "ml.combust.mleap" %% "mleap-spark" % "0.10.0"

libraryDependencies += "ml.combust.mleap" %% "mleap-runtime" % "0.10.0"

libraryDependencies += "io.spray" %%  "spray-json" % "1.3.4"


assemblyMergeStrategy in assembly :={
  case PathList("org", "apache", "spark", "unused", "UnusedStubClass.class")  => MergeStrategy.first
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}

// for debugging sbt problems
logLevel := Level.Debug