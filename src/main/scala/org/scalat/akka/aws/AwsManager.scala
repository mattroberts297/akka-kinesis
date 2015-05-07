package org.scalat.akka.aws

import akka.actor.{Props, Actor}
import com.amazonaws.services.kinesis.{AmazonKinesisAsync, AmazonKinesisAsyncClient}

class AwsManager(underlyingFactory: => AmazonKinesisAsync = new AmazonKinesisAsyncClient()) extends Actor {
  import Aws._
  override def receive: Receive = {
    case Kinesis => try {
      val commander = sender()
      val underlying = new AmazonKinesisAsyncClient()
      context.actorOf(Props(classOf[KinesisClient], commander, underlying))
    } catch {
      case exception: Exception => sender() ! CommandFailed(Kinesis, exception)
    }
  }
}
