lazy val root = (project in file(".")).settings(
  name := "kinesis4akka",
  organization := "org.typetastic",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.11.6",
  scalacOptions ++= Seq("-deprecation"),
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-actor" % "2.3.10",
    "com.typesafe.akka" %% "akka-testkit" % "2.3.10",
    "com.amazonaws" % "aws-java-sdk-kinesis" % "1.9.33",
    "com.amazonaws" % "amazon-kinesis-client" % "1.2.1",
    "org.scalatest" %% "scalatest" % "2.2.1" % "test",
    "org.mockito" % "mockito-core" % "1.10.19" % "test",
    "junit" % "junit" % "4.12" % "test"
  )
)
