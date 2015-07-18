package org.typetastic.aws

import akka.actor.Actor
import akka.actor.Props
import akka.event.Logging
import akka.event.LoggingReceive
import com.amazonaws.services.kinesis.AmazonKinesisAsyncClient
import org.typetastic.aws.handlers.PromiseHandlerFactory
import org.typetastic.aws.kinesis.AmazonKinesisAsyncClientWrapper
import org.typetastic.aws.kinesis.KinesisClient
import org.typetastic.aws.kinesis.converters.KinesisConverter
import org.typetastic.aws.kinesis.model.CommandFailed
import org.typetastic.aws.kinesis.model.CreateKinesisClient


class AwsManager extends Actor {

  import context._

  val log = Logging(system, this)

  override def receive: Receive = LoggingReceive {
    case command: CreateKinesisClient => try {
      val commander = sender()
      val wrapper = new AmazonKinesisAsyncClientWrapper(
        new AmazonKinesisAsyncClient,
        new KinesisConverter,
        new PromiseHandlerFactory)
      context.actorOf(Props(classOf[KinesisClient], commander, wrapper))
    } catch {
      case exception: Exception => sender() ! CommandFailed(command, exception)
    }
  }
}
