package org.typedsolutions.aws

import akka.actor.Actor
import akka.event.Logging
import akka.event.LoggingReceive
import akka.io.IO
import org.typedsolutions.aws.kinesis.model._

class DemoActor extends Actor {
  import context._

  val log = Logging(system, this)

  override def preStart(): Unit = {
    log.debug("In DemoActor preStart()")
    IO(Aws) ! CreateKinesisClient
  }

  def receive: Receive = LoggingReceive {
    case KinesisClientCreated =>
      log.debug("Client Created")
      sender() ! ListStreamsRequest(None, None)
    case response: ListStreamsResponse =>
      log.debug(s"Got records $response")
    case CommandFailed(command, exception) =>
      log.error(exception, s"Command failed: $command")
  }
}
