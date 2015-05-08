package org.scalat.akka.aws

import akka.actor.{Terminated, ActorRef, Actor}
import akka.event.Logging
import com.amazonaws.services.kinesis.AmazonKinesisAsync
import com.amazonaws.services.kinesis.model.{ListStreamsRequest, ListStreamsResult => UnderlyingListStreamsResult}
import com.amazonaws.services.kinesis.model.{DescribeStreamRequest, DescribeStreamResult => UnderlyingDescribeStreamResult}
import org.scalat.akka.aws.Aws._

import scala.collection.JavaConverters._
import scala.concurrent.Promise
import scala.util.{Failure, Success}

class KinesisClient(commander: ActorRef, underlying: AmazonKinesisAsync) extends Actor {
  import context._

  val log = Logging(system, this)

  context watch commander

  commander ! KinesisResult

  override def receive: Receive = {
    case listStreams: ListStreams => {
      log.info(s"received $listStreams")
      handle(sender(), listStreams)
    }
    case describeStream: DescribeStream => ???
    case Terminated(`commander`) => context.stop(self)
  }

  def handle(sender: ActorRef, listStreams: ListStreams): Unit = {
    try {
      val request = new ListStreamsRequest()
      listStreams.exclusiveStartStreamName.map(request.setExclusiveStartStreamName(_))
      listStreams.limit.map(request.setLimit(_))
      val promise = Promise[UnderlyingListStreamsResult]()
      val handler = PromiseBasedAsyncHandler[ListStreamsRequest, UnderlyingListStreamsResult](promise)
      underlying.listStreamsAsync(request, handler)
      val future = promise.future
      future.onComplete {
        case Success(result) => {
          sender ! ListStreamsResult(result.getStreamNames.asScala.toList, result.getHasMoreStreams)
        }
        case Failure(throwable) => {
          sender ! CommandFailed(listStreams, throwable)
        }
      }
    } catch {
      case throwable: Throwable => sender ! CommandFailed(listStreams, throwable)
    }
  }

  def handle(sender: ActorRef, describeStream: DescribeStream): Unit = {
    try {
      val request = createDescribeStreamRequest(describeStream)
      val promise = Promise[UnderlyingDescribeStreamResult]()
      val future = promise.future
      underlying.describeStreamAsync(request, PromiseBasedAsyncHandler(promise))
      future.onComplete {
        case Success(result) => {
          ??? // TODO: Implement
        }
        case Failure(throwable) => {
          sender ! CommandFailed(describeStream, throwable)
        }
      }

    } catch {
      case throwable: Throwable => sender ! CommandFailed(describeStream, throwable)
    }
  }

  def createDescribeStreamRequest(describeStream: DescribeStream): DescribeStreamRequest = {
    val request = new DescribeStreamRequest()
    request.setStreamName(describeStream.streamName)
    describeStream.exclusiveStartShardId.map(request.setExclusiveStartShardId(_))
    describeStream.limit.map(request.setLimit(_))
    request
  }
}
