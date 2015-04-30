package org.scalaq.akka.aws

import akka.actor.{Props, Actor}
import com.amazonaws.services.kinesis.AmazonKinesisAsyncClient

class KinesisManager extends Actor {
  import Kinesis._
  override def receive: Receive = {
    case CreateClient => try {
      val commander = sender()
      val underlying = new AmazonKinesisAsyncClient()
      context.actorOf(Props(classOf[KinesisClient], commander, underlying))
    } catch {
      case exception: Exception => sender() ! CommandFailed(CreateClient, exception)
    }
  }
}
