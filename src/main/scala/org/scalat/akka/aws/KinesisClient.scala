package org.scalat.akka.aws

import akka.actor.{Terminated, ActorRef, Actor}
import com.amazonaws.services.kinesis.AmazonKinesisAsync
import com.amazonaws.services.kinesis.model.{ListStreamsRequest, ListStreamsResult => UnderlyingListStreamsResult}
import org.scalat.akka.aws.Aws._

import scala.collection.JavaConverters._
import scala.concurrent.Promise
import scala.util.{Failure, Success}

class KinesisClient(commander: ActorRef, underlying: AmazonKinesisAsync) extends Actor {
  import context._
  context watch commander
  commander ! KinesisResult

  override def receive: Receive = {
    case listStreams: ListStreams => handle(listStreams)
    case describeStream: DescribeStream => ???
    case Terminated(`commander`) => context.stop(self)
  }

  def handle(listStreams: ListStreams): Unit = {
    val request = new ListStreamsRequest()
    listStreams.exclusiveStartStreamName.map(request.setExclusiveStartStreamName(_))
    listStreams.limit.map(request.setLimit(_))
    val promise = Promise[UnderlyingListStreamsResult]()
    val handler = new PromiseBasedAsyncHandler[ListStreamsRequest, UnderlyingListStreamsResult](promise)
    underlying.listStreamsAsync(request, handler)
    val future = promise.future
    future.onComplete {
      case Success(result) => {
        commander ! ListStreamsResult(result.getStreamNames.asScala.toList, result.getHasMoreStreams)
      }
      case Failure(throwable) => {
        commander ! CommandFailed(listStreams, throwable)
      }
    }
  }
}
