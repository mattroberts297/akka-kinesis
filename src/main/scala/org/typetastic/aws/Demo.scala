package org.typetastic.aws

import akka.actor.{Props, ActorSystem}

object Demo extends App {
  val system = ActorSystem("kinesis-system")
  val myActor = system.actorOf(Props[DemoActor], "demo-actor")
}
