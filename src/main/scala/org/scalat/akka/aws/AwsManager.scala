package org.scalat.akka.aws

import akka.actor.{Props, Actor}
import akka.event.Logging
import com.amazonaws.services.kinesis.{AmazonKinesisAsync, AmazonKinesisAsyncClient}


class AwsManager(factory: AwsFactory) extends Actor {

  import context._
  import Aws._

  val log = Logging(system, this)

  override def receive: Receive = {
    case Kinesis => try {
      log.info(s"received $Kinesis")
      val commander = sender()
      val underlying = factory.kinesis()
      context.actorOf(Props(classOf[KinesisClient], commander, underlying))
    } catch {
      case exception: Exception => sender() ! CommandFailed(Kinesis, exception)
    }
  }
}
