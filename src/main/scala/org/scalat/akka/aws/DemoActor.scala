package org.scalat.akka.aws

import akka.actor.{ActorRef, Actor}
import akka.event.Logging
import akka.io.IO
import org.scalat.akka.aws.Aws._

class DemoActor extends Actor {
  import context._

  val log = Logging(system, this)

  IO(Aws(Region.EU_WEST_1)) ! Kinesis

  def receive = {
    case KinesisResult => {
      log.info(s"received $KinesisResult")
      val client = sender()
      client ! ListStreams()
      context.become(hasClient(client))
    }
    case _ => log.info("received unknown message")
  }

  def hasClient(client: ActorRef): Receive = {
    case result: ListStreamsResult => {
      log.info(s"received $result")
      client ! DescribeStream(result.streamNames.head)
    }
    case result: DescribeStreamResult => {
      log.info(s"received $result")
      context.system.shutdown()
    }
    case _ => log.info("received unknown message")
  }
}