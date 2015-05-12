package org.scalat.akka.aws

import akka.actor.{Terminated, ActorRef, Actor}
import akka.event.Logging
import com.amazonaws.services.kinesis.AmazonKinesisAsync
import com.amazonaws.services.kinesis.model.{ListStreamsRequest, ListStreamsResult => UnderlyingListStreamsResult}
import com.amazonaws.services.kinesis.model.{DescribeStreamRequest, DescribeStreamResult => UnderlyingDescribeStreamResult}
import com.amazonaws.services.kinesis.model.{Shard => UnderlyingShard}
import com.amazonaws.services.kinesis.model.{HashKeyRange => UnderlyingHashKeyRange}
import com.amazonaws.services.kinesis.model.{SequenceNumberRange => UnderlyingSequenceNumberRange}
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
    case describeStream: DescribeStream => {
      log.info(s"received $describeStream")
      handle(sender(), describeStream)
    }
    case Terminated(`commander`) => {
      log.info(s"received Terminated")
      underlying.shutdown()
      context.stop(self)
    }
  }

  // TODO: Pull out to Handlers trait.
  def handle(sender: ActorRef, listStreams: ListStreams): Unit = {
    try {
      val request = new ListStreamsRequest()
      listStreams.exclusiveStartStreamName.map(request.setExclusiveStartStreamName(_))
      listStreams.limit.map(request.setLimit(_))
      val promise = Promise[UnderlyingListStreamsResult]()
      val handler = PromiseAsyncHandler[ListStreamsRequest, UnderlyingListStreamsResult](promise)
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
      val request = convert(describeStream)
      val promise = Promise[UnderlyingDescribeStreamResult]()
      val future = promise.future
      underlying.describeStreamAsync(request, PromiseAsyncHandler(promise))
      future.onComplete {
        case Success(result) => {
          sender ! convert(result)
        }
        case Failure(throwable) => {
          sender ! CommandFailed(describeStream, throwable)
        }
      }
    } catch {
      case throwable: Throwable => {
        sender ! CommandFailed(describeStream, throwable)
      }
    }
  }

  // TODO: Pull out to trait Converters and test.
  def convert(result: UnderlyingDescribeStreamResult): DescribeStreamResult = {
    val description = result.getStreamDescription
    val shards = description.getShards.asScala.map(convert).toList
    DescribeStreamResult(
      description.getStreamName,
      description.getStreamARN,
      description.getStreamStatus,
      shards)
  }

  def convert(underlying: UnderlyingShard): Shard = {
    Shard(
      underlying.getShardId,
      underlying.getAdjacentParentShardId,
      underlying.getParentShardId,
      convert(underlying.getHashKeyRange),
      convert(underlying.getSequenceNumberRange)
    )
  }

  def convert(underlying: UnderlyingHashKeyRange): HashKeyRange = {
    HashKeyRange(underlying.getStartingHashKey, underlying.getEndingHashKey)
  }

  def convert(underlying: UnderlyingSequenceNumberRange): SequenceNumberRange = {
    SequenceNumberRange(
      underlying.getStartingSequenceNumber,
      underlying.getEndingSequenceNumber)
  }

  def convert(describeStream: DescribeStream): DescribeStreamRequest = {
    val request = new DescribeStreamRequest()
    request.setStreamName(describeStream.streamName)
    describeStream.exclusiveStartShardId.map(request.setExclusiveStartShardId(_))
    describeStream.limit.map(request.setLimit(_))
    request
  }
}
