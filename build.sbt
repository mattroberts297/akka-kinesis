lazy val root = (project in file(".")).settings(
  name := "akka-aws",
  organization := "org.scalat.akka",
  version := "0.1.0",
  scalaVersion := "2.11.5",
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-actor" % "2.3.9",
    "com.amazonaws" % "aws-java-sdk-kinesis" % "1.9.33",
    "org.scalatest" %% "scalatest" % "2.2.1" % "test"
  )
)
