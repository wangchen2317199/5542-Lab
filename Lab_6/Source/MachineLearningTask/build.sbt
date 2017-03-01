name := "T6_-_Question_1"

version := "1.0"

scalaVersion := "2.11.8"

resolvers += "OpenIMAJ maven releases repository" at "http://maven.openimaj.org"
resolvers += "OpenIMAJ maven snapshots repository" at "http://snapshots.openimaj.org"

libraryDependencies ++= Seq(
  "org.openimaj" % "klt-tracker" % "1.3.5" % "compile",
  "org.openimaj" % "core" % "1.3.5" % "compile",
  "org.openimaj" % "core-math" % "1.3.5" % "compile",
  "org.openimaj" % "core-image" % "1.3.5" % "compile",
  "org.openimaj" % "image-processing" % "1.3.5" % "compile",
  "org.openimaj" % "clustering" % "1.3.5" % "compile",
  "org.openimaj" % "image-local-features" % "1.3.5" % "compile",
  "org.openimaj" % "image-feature-extraction" % "1.3.5" % "compile",
  "junit" % "junit" % "4.8.2" % "test",
  "org.reflections" % "reflections" % "0.9.10",
  "com.uwyn" % "jhighlight" % "1.0",
  "org.xhtmlrenderer" % "core-renderer" % "R8pre2",
  "org.openimaj.hardware" % "kinect" % "1.3.5" % "compile",
  "com.google.zxing" % "javase" % "2.0" % "compile",
  "com.google.zxing" % "core" % "2.0" % "compile",
  "org.apache.spark" %% "spark-core" % "2.0.0" % "provided",
  "org.apache.spark" %% "spark-streaming" % "2.0.0",
  "org.apache.spark" %% "spark-mllib" % "2.0.0",
  "org.apache.spark" % "spark-sql_2.11" % "2.0.0"
)