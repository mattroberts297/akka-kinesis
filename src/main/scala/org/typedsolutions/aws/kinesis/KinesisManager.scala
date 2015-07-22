package org.typedsolutions.aws.kinesis

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.ActorRefFactory
import akka.actor.Props
import akka.event.Logging
import akka.event.LoggingReceive
import org.typedsolutions.aws.kinesis.model._
import org.typedsolutions.aws.util.ActorNaming._

class KinesisManager(factory: (ActorRefFactory, ActorRef, CreateKinesisClient) => ActorRef) extends Actor {

  def this() = this(KinesisManager.DefaultFactoryMethod)

  import context._

  val log = Logging(system, this)

  override def receive: Receive = LoggingReceive {
    case createKinesisClient: CreateKinesisClient => {
      try {
        val commander = sender()
        factory(context, commander, createKinesisClient)
      } catch {
        case exception: Exception => sender() ! CommandFailed(createKinesisClient, exception)
      }
    }
  }
}

object KinesisManager {
  def DefaultFactoryMethod(
    factory: ActorRefFactory,
    commander: ActorRef,
    createKinesisClient: CreateKinesisClient): ActorRef = {

    import factory._
    val client = AmazonKinesisClient(clientConfiguration = createKinesisClient.configuration)
    createKinesisClient.region.map(Region.underlying).map(client.underlying.setRegion)
    createKinesisClient.endpoint.map(client.underlying.setEndpoint)
    factory.actorOf(
      Props(classOf[AmazonKinesisActor], commander, client),
      uniqueName[AmazonKinesisActor])
  }
}
