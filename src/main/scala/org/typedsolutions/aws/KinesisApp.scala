package org.typedsolutions.aws

import akka.actor.{Props, ActorSystem}
import org.typedsolutions.aws.util.ActorNaming._

import scala.io.StdIn

object KinesisApp extends App {
  val system = ActorSystem("kinesis-app")
  val myActor = system.actorOf(Props[DemoActor], name[DemoActor])
  println("Press any key to shutdown")
  StdIn.readLine()
  system.shutdown()
}
