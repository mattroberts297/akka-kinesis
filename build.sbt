lazy val akkaVersion = "2.3.12"

lazy val root = (project in file(".")).settings(
  name := "akka-kinesis",
  organization := "org.typedsolutions",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.11.7",
  crossScalaVersions := Seq("2.10.5", "2.11.7"),
  scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature"),
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
    "com.amazonaws" % "aws-java-sdk-kinesis" % "1.9.33",
    "org.scalatest" %% "scalatest" % "2.2.1" % "test",
    "org.mockito" % "mockito-core" % "1.10.19" % "test",
    "junit" % "junit" % "4.12" % "test"
  )
)
