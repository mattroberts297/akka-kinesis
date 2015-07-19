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
import org.typetastic.aws.kinesis.model._
import org.typetastic.aws.util.ActorNaming._

class AwsManager extends Actor {

  import context._

  val log = Logging(system, this)

  override def receive: Receive = LoggingReceive {
    // TODO: Try and use system ExecutionService. Failing that use global.
    // TODO: Let user specify region.
    case CreateKinesisClient => {
      try {
        val commander = sender()
        val wrapper = new AmazonKinesisAsyncClientWrapper(
          new AmazonKinesisAsyncClient,
          new KinesisConverter,
          new PromiseHandlerFactory)
        context.actorOf(Props(classOf[KinesisClient], commander, wrapper), uniqueName[KinesisClient])
      } catch {
        case exception: Exception => sender() ! CommandFailed(CreateKinesisClient, exception)
      }
    }
  }
}
