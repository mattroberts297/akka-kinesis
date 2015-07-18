package org.typetastic.aws


import akka.actor.{ActorRef, Actor}
import akka.event.Logging
import akka.event.LoggingReceive
import akka.io.IO

class DemoActor extends Actor {
  import context._

  val log = Logging(system, this)

  def receive = LoggingReceive {
    ???
  }
}
