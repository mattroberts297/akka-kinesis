package org.scalat.akka.aws

import akka.actor.{Terminated, ActorRef, Actor}
import akka.event.Logging
import com.amazonaws.services.kinesis.AmazonKinesisAsync

import com.amazonaws.services.kinesis.model.ListStreamsRequest
import com.amazonaws.services.kinesis.model.{ListStreamsResult => UnderlyingListStreamsResult}
import com.amazonaws.services.kinesis.model.DescribeStreamRequest
import com.amazonaws.services.kinesis.model.{DescribeStreamResult => UnderlyingDescribeStreamResult}
import com.amazonaws.services.kinesis.model.GetShardIteratorRequest
import com.amazonaws.services.kinesis.model.{GetShardIteratorResult => UnderlyingGetShardIteratorResult}
import com.amazonaws.services.kinesis.model.{Shard => UnderlyingShard}
import com.amazonaws.services.kinesis.model.{HashKeyRange => UnderlyingHashKeyRange}
import com.amazonaws.services.kinesis.model.{SequenceNumberRange => UnderlyingSequenceNumberRange}

import org.scalat.akka.aws.Aws._

import scala.collection.JavaConverters._
import scala.concurrent.{Future, Promise}
import scala.util.{Failure, Success}

class KinesisClient(commander: ActorRef, underlying: AmazonKinesisAsync) extends Actor {
  import context._

  val log = Logging(system, this)

  context watch commander

  commander ! KinesisResult

  override def receive: Receive = {
    case command: ListStreams => {
      log.debug(s"Received $command")
      Akkafy(sender())(command)(listStreams)
    }
    case command: DescribeStream => {
      log.debug(s"Received $command")
      Akkafy(sender())(command)(describeStream)
    }
    case command: GetShardIterator => {
      log.debug(s"Received $command")
      Akkafy(sender())(command)(getShardIterator)
    }
    case Terminated(`commander`) => {
      underlying.shutdown()
      context.stop(self)
    }
  }

  def Akkafy
      [A <: Command, B <: Result]
      (ref: ActorRef)
      (command: A)
      (thunk: (ActorRef, A) => Future[B]): Unit = {
    try {
      thunk(ref, command).onComplete {
        case Success(result) => {
          ref ! result
        }
        case Failure(throwable) => {
          ref ! CommandFailed(command, throwable)
        }
      }
    } catch {
      case throwable: Throwable => ref ! CommandFailed(command, throwable)
    }
  }

  // TODO: Pull out to Handlers trait and test.
  def listStreams(sender: ActorRef, listStreams: ListStreams): Future[ListStreamsResult] = {
    val request = convert(listStreams)
    val promise = Promise[UnderlyingListStreamsResult]()
    underlying.listStreamsAsync(request, PromiseAsyncHandler(promise))
    val future = promise.future
    future.map(convert)
  }

  def describeStream(sender: ActorRef, describeStream: DescribeStream): Future[DescribeStreamResult] = {
    val request = convert(describeStream)
    val promise = Promise[UnderlyingDescribeStreamResult]()
    val future = promise.future
    underlying.describeStreamAsync(request, PromiseAsyncHandler(promise))
    future.map(convert)
  }

  def getShardIterator(sender: ActorRef, getShardIterator: GetShardIterator): Future[GetShardIteratorResult] = {
    val request = convert(getShardIterator)
    val promise = Promise[UnderlyingGetShardIteratorResult]()
    val future = promise.future
    underlying.getShardIteratorAsync(request, PromiseAsyncHandler(promise))
    future.map(convert)
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

  def convert(listStreams: ListStreams): ListStreamsRequest = {
    val request = new ListStreamsRequest()
    listStreams.exclusiveStartStreamName.map(request.setExclusiveStartStreamName(_))
    listStreams.limit.map(request.setLimit(_))
    request
  }

  def convert(underlying: UnderlyingListStreamsResult): ListStreamsResult = {
    ListStreamsResult(underlying.getStreamNames.asScala.toList, underlying.getHasMoreStreams)
  }

  def convert(getShardIterator: GetShardIterator): GetShardIteratorRequest = {
    val request = new GetShardIteratorRequest
    request.setShardId(getShardIterator.shardId)
    request.setShardIteratorType(ShardIteratorType.underlying(
      getShardIterator.shardIteratorType))
    request.setStreamName(getShardIterator.streamName)
    getShardIterator.startingSequenceNumber.map(
      request.setStartingSequenceNumber(_))
    request
  }

  def convert(result: UnderlyingGetShardIteratorResult): GetShardIteratorResult = {
    GetShardIteratorResult(result.getShardIterator)
  }
}
