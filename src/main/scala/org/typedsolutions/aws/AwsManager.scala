package org.typedsolutions.aws

import akka.actor.Actor
import akka.actor.Props
import akka.event.Logging
import akka.event.LoggingReceive
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.services.kinesis.AmazonKinesisAsyncClient
import org.typedsolutions.aws.handlers.PromiseHandlerFactory
import org.typedsolutions.aws.kinesis.AmazonKinesisAsyncClientWrapper
import org.typedsolutions.aws.kinesis.KinesisClient
import org.typedsolutions.aws.kinesis.converters.KinesisConverter
import org.typedsolutions.aws.kinesis.model._
import org.typedsolutions.aws.util.ActorNaming._
import org.typedsolutions.aws.util.ExecutionContextWrapper

class AwsManager extends Actor {

  import context._

  val log = Logging(system, this)

  val providerChain = new DefaultAWSCredentialsProviderChain

  val executorService = new ExecutionContextWrapper(system.dispatcher)

  override def receive: Receive = LoggingReceive {
    // TODO: Let user specify region.
    case CreateKinesisClient => {
      try {
        val commander = sender()
        val wrapper = new AmazonKinesisAsyncClientWrapper(
          new AmazonKinesisAsyncClient(providerChain, executorService),
          new KinesisConverter,
          new PromiseHandlerFactory)
        context.actorOf(Props(classOf[KinesisClient], commander, wrapper), uniqueName[KinesisClient])
      } catch {
        case exception: Exception => sender() ! CommandFailed(CreateKinesisClient, exception)
      }
    }
  }
}
