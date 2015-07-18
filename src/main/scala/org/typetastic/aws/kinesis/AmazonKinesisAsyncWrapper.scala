package org.typetastic.aws.kinesis

import org.typetastic.aws.kinesis.model._

import scala.concurrent.Future

trait AmazonKinesisAsyncWrapper {
  def createStream(request: CreateStreamRequest): Future[CreateStreamResponse]

  def deleteStream(request: DeleteStreamRequest): Future[DeleteStreamResponse]

  def describeStream(request: DescribeStreamRequest): Future[DescribeStreamResponse]

  def getRecords(request: GetRecordsRequest): Future[GetRecordsResponse]

  def getShardIterator(request: GetShardIteratorRequest): Future[GetShardIteratorResponse]

  def listStreams(request: ListStreamsRequest): Future[ListStreamsResponse]

  def mergeShards(request: MergeShardsRequest): Future[MergeShardsResponse]

  def putRecord(request: PutRecordRequest): Future[PutRecordResponse]

  def putRecords(request: PutRecordsRequest): Future[PutRecordsResponse]

  def splitShard(request: SplitShardRequest): Future[SplitShardResponse]
}
