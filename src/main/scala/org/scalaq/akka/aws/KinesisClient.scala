package org.scalaq.akka.aws

import akka.actor.{Terminated, ActorRef, Actor}
import com.amazonaws.services.kinesis.AmazonKinesisAsync
import org.scalaq.akka.aws.Kinesis._

class KinesisClient(commander: ActorRef, underlying: AmazonKinesisAsync) extends Actor {
  context watch commander
  commander ! ClientCreated

  override def receive: Receive = {
    case c: Command => {
      case listStreams: ListStreams => ???
      case describeStream: DescribeStream => ???
    }
    case Terminated(`commander`) => context.stop(self)
  }
}
