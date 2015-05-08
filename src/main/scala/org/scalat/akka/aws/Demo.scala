package org.scalat.akka.aws

import akka.actor.{Props, ActorSystem}

object Demo extends App {
  val system = ActorSystem("demoSystem")
  val myActor = system.actorOf(Props[DemoActor], "demoActor")
}
