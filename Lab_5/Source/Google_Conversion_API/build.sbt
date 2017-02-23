name := "Tutorial4_Question2"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.json" % "json" % "20160810",
  "org.apache.kafka" % "kafka-clients" % "0.10.0.1",
  "log4j" % "log4j" % "1.2.17",
  "org.apache.kafka" % "kafka_2.10" % "0.8.1.1",
  "junit" % "junit" % "4.11" % "test",
  "com.google.guava" % "guava" % "19.0",
  "javax.servlet" % "javax.servlet-api" % "3.0.1" % "provided"
)