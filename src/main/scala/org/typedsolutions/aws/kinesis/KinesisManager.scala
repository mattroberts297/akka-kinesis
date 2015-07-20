package org.typedsolutions.aws.kinesis

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.ActorRefFactory
import akka.actor.Props
import akka.event.Logging
import akka.event.LoggingReceive
import org.typedsolutions.aws.kinesis.model._
import org.typedsolutions.aws.util.ActorNaming._

class KinesisManager(factory: (ActorRefFactory, ActorRef) => ActorRef) extends Actor {

  def this() = this(KinesisManager.DefaultFactoryMethod)

  import context._

  val log = Logging(system, this)

  override def receive: Receive = LoggingReceive {
    // TODO: Let user specify region, endpoint or time.
    case CreateKinesisClient => {
      try {
        val commander = sender()
        factory(context, commander)
      } catch {
        case exception: Exception => sender() ! CommandFailed(CreateKinesisClient, exception)
      }
    }
  }
}

object KinesisManager {
  def DefaultFactoryMethod(factory: ActorRefFactory, commander: ActorRef): ActorRef = {
    import factory._
    factory.actorOf(
      Props(classOf[AmazonKinesisActor], commander, AmazonKinesisClient()),
      uniqueName[AmazonKinesisActor])
  }
}
