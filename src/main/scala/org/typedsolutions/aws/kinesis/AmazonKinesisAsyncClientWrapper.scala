package org.typedsolutions.aws.kinesis

import com.amazonaws.AmazonWebServiceRequest
import com.amazonaws.services.kinesis.{AmazonKinesisAsync => Underlying}
import org.typedsolutions.aws.handlers.PromiseHandler
import org.typedsolutions.aws.handlers.PromiseHandlerFactory
import org.typedsolutions.aws.kinesis.converters.KinesisConverter
import org.typedsolutions.aws.kinesis.model._

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

class AmazonKinesisAsyncClientWrapper(
    val underlying: Underlying,
    val converter: KinesisConverter,
    val factory: PromiseHandlerFactory)(
    implicit ec: ExecutionContext) extends AmazonKinesisAsyncWrapper {
  import converter._
  import factory._

  def createStream(request: CreateStreamRequest): Future[CreateStreamResponse] = {
    invoke(request)(toAws)(underlying.createStreamAsync)(Void => CreateStreamResponse())
  }

  def deleteStream(request: DeleteStreamRequest): Future[DeleteStreamResponse] = {
    invoke(request)(toAws)(underlying.deleteStreamAsync)(Void => DeleteStreamResponse())
  }

  def describeStream(request: DescribeStreamRequest): Future[DescribeStreamResponse] = {
    invoke(request)(toAws)(underlying.describeStreamAsync)(fromAws)
  }

  def getRecords(request: GetRecordsRequest): Future[GetRecordsResponse] = {
    invoke(request)(toAws)(underlying.getRecordsAsync)(fromAws)
  }

  def getShardIterator(request: GetShardIteratorRequest): Future[GetShardIteratorResponse] = {
    invoke(request)(toAws)(underlying.getShardIteratorAsync)(fromAws)
  }

  def listStreams(request: ListStreamsRequest): Future[ListStreamsResponse] = {
    invoke(request)(toAws)(underlying.listStreamsAsync)(fromAws)
  }

  def mergeShards(request: MergeShardsRequest): Future[MergeShardsResponse] = {
    invoke(request)(toAws)(underlying.mergeShardsAsync)(Void => MergeShardsResponse())
  }

  def putRecord(request: PutRecordRequest): Future[PutRecordResponse] = {
    invoke(request)(toAws)(underlying.putRecordAsync)(fromAws)
  }

  def putRecords(request: PutRecordsRequest): Future[PutRecordsResponse] = {
    invoke(request)(toAws)(underlying.putRecordsAsync)(fromAws)
  }

  def splitShard(request: SplitShardRequest): Future[SplitShardResponse] = {
    invoke(request)(toAws)(underlying.splitShardAsync)(Void => SplitShardResponse())
  }

  private def invoke
      [Request, UnderlyingRequest <: AmazonWebServiceRequest, UnderlyingResponse, Response]
      (request: Request)
      (toAws: Request => UnderlyingRequest)
      (method: (UnderlyingRequest, PromiseHandler[UnderlyingRequest, UnderlyingResponse]) => _)
      (fromAws: UnderlyingResponse => Response): Future[Response] = {
    val handler = create[UnderlyingRequest, UnderlyingResponse]()
    val underlyingRequest = toAws(request)
    method(underlyingRequest, handler)
    handler.future.map(fromAws)
  }
}