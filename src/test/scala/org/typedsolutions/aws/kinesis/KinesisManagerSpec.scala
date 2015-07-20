package org.typedsolutions.aws.kinesis

import akka.actor.ActorRef
import akka.actor.ActorRefFactory
import akka.actor.Deploy
import akka.actor.Props
import akka.testkit.TestActorRef
import org.typedsolutions.aws.kinesis.model.CommandFailed
import org.typedsolutions.aws.util.ActorNaming._
import org.typedsolutions.aws.util.AkkaSpec
import org.typedsolutions.aws.kinesis.model.{CreateKinesisClient, KinesisClientCreated}

class KinesisManagerSpec extends AkkaSpec {
  val classUnderTest = classOf[KinesisManager].getSimpleName

  s"The $classUnderTest" when {
    s"sent a $CreateKinesisClient" should {
      s"create a child that replies with a $KinesisClientCreated" in new SuccessContext {
        manager ! CreateKinesisClient
        expectMsg(KinesisClientCreated)
        lastSender should not be (manager)
      }

      s"respond with a CommandFailed on failure" in new FailureContext {
        manager ! CreateKinesisClient
        expectMsg(CommandFailed(CreateKinesisClient, exception))
        lastSender should be (manager)
      }
    }
  }

  trait SuccessContext {
    val manager = TestActorRef(
      props = Props(classOf[KinesisManager]).withDeploy(Deploy.local),
      name = uniqueName[KinesisManager])
  }

  trait FailureContext {
    val exception = new RuntimeException

    def FailingFactoryMethod(factory: ActorRefFactory, commander: ActorRef): ActorRef = {
      throw exception
    }

    val manager = TestActorRef(
      props = Props(classOf[KinesisManager], FailingFactoryMethod _).withDeploy(Deploy.local),
      name = uniqueName[KinesisManager])
  }
}
