package org.typetastic.aws.kinesis

import com.amazonaws.services.kinesis.{AmazonKinesisAsync => Underlying}
import com.amazonaws.services.kinesis.model.{DescribeStreamResult => UnderlyingDescribeStreamResult}
import org.typetastic.aws.handlers.PromiseHandlerFactory
import org.typetastic.aws.kinesis.model._

import scala.concurrent.{ExecutionContext, Promise, Future}

// TODO Implement after converters.
class KinesisClient(
    val underlying: Underlying,
    val converter: ModelConverter,
    val factory: PromiseHandlerFactory)(
    implicit ec: ExecutionContext) {
  import converter._
  import factory._

  def createStream(request: CreateStreamRequest): Future[Unit] = {
    val promise = Promise[Void]()
    underlying.createStreamAsync(toAws(request), create(promise))
    promise.future.map(_ => Unit)
  }

  def deleteStream(request: DeleteStreamRequest): Future[Unit] = {
    val promise = Promise[Void]()
    underlying.deleteStreamAsync(toAws(request), create(promise))
    promise.future.map(_ => Unit)
  }

  def describeStream(request: DescribeStreamRequest): Future[DescribeStreamResponse] = {
    val promise = Promise[UnderlyingDescribeStreamResult]()
    underlying.describeStreamAsync(toAws(request), create(promise))
    promise.future.map(fromAws)
  }

  def getRecords(request: GetRecordsRequest): Future[GetRecordsResponse] = {
    ???
  }

  def getShardIterator(request: GetShardIteratorRequest): Future[GetShardIteratorResponse] = {
    ???
  }

  def listStreams(request: ListStreamsRequest): Future[ListStreamsResponse] = {
    ???
  }

  def mergeShards(request: MergeShardsRequest): Future[Unit] = {
    ???
  }

  def putRecord(request: PutRecordRequest): Future[PutRecordResponse] = {
    ???
  }

  def putRecords(request: PutRecordsRequest): Future[PutRecordsResponse] = {
    ???
  }

  def splitShard(request: SplitShardRequest): Future[Unit] = {
    ???
  }
}
