package org.typetastic.aws.kinesis

import com.amazonaws.services.kinesis.{AmazonKinesisAsync => Underlying}
import com.amazonaws.services.kinesis.model.{DescribeStreamResult => UnderlyingDescribeStreamResult}
import com.amazonaws.services.kinesis.model.{GetRecordsResult => UnderlyingGetRecordsResult}
import com.amazonaws.services.kinesis.model.{GetShardIteratorResult => UnderlyingGetShardIteratorResult}
import com.amazonaws.services.kinesis.model.{ListStreamsResult => UnderlyingListStreamsResult}
import com.amazonaws.services.kinesis.model.{PutRecordResult => UnderlyingPutRecordResult}
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
    val promise = Promise[UnderlyingGetRecordsResult]()
    underlying.getRecordsAsync(toAws(request), create(promise))
    promise.future.map(fromAws)
  }

  def getShardIterator(request: GetShardIteratorRequest): Future[GetShardIteratorResponse] = {
    val promise = Promise[UnderlyingGetShardIteratorResult]()
    underlying.getShardIteratorAsync(toAws(request), create(promise))
    promise.future.map(fromAws)
  }

  def listStreams(request: ListStreamsRequest): Future[ListStreamsResponse] = {
    val promise = Promise[UnderlyingListStreamsResult]()
    underlying.listStreamsAsync(toAws(request), create(promise))
    promise.future.map(fromAws)
  }

  def mergeShards(request: MergeShardsRequest): Future[MergeShardsResponse] = {
    val promise = Promise[Void]()
    underlying.mergeShardsAsync(toAws(request), create(promise))
    promise.future.map(_ => MergeShardsResponse())
  }

  def putRecord(request: PutRecordRequest): Future[PutRecordResponse] = {
    val promise = Promise[UnderlyingPutRecordResult]()
    underlying.putRecordAsync(toAws(request), create(promise))
    promise.future.map(fromAws)
  }

  def putRecords(request: PutRecordsRequest): Future[PutRecordsResponse] = {
    ???
  }

  def splitShard(request: SplitShardRequest): Future[Unit] = {
    ???
  }
}
