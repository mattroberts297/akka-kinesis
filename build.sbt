lazy val root = (project in file(".")).settings(
  name := "aws-kinesis",
  organization := "org.typetastic.akka",
  version := "0.1.0",
  scalaVersion := "2.11.6",
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-actor" % "2.3.10",
    "com.amazonaws" % "aws-java-sdk-kinesis" % "1.9.33",
    "com.amazonaws" % "amazon-kinesis-client" % "1.2.1",
    "org.scalatest" %% "scalatest" % "2.2.1" % "test",
    "org.mockito" % "mockito-core" % "1.10.19"
  )
)
