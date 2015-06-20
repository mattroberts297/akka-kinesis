package org.typetastic.aws.kinesis.model

import com.amazonaws.services.kinesis.model.{CreateStreamRequest => UnderlyingCreateStreamRequest}
import com.amazonaws.services.kinesis.model.{DeleteStreamRequest => UnderlyingDeleteStreamRequest}
import com.amazonaws.services.kinesis.model.{DescribeStreamRequest => UnderlyingDescribeStreamRequest}
import com.amazonaws.services.kinesis.model.{DescribeStreamResult => UnderlyingDescribeStreamResult}
import com.amazonaws.services.kinesis.model.{Shard => UnderlyingShard}
import com.amazonaws.services.kinesis.model.{HashKeyRange => UnderlyingHashKeyRange}
import com.amazonaws.services.kinesis.model.{SequenceNumberRange => UnderlyingSequenceNumberRange}
import com.amazonaws.services.kinesis.model.{GetRecordsRequest => UnderlyingGetRecordsRequest}
import com.amazonaws.services.kinesis.model.{GetRecordsResult => UnderlyingGetRecordsResult}
import com.amazonaws.services.kinesis.model.{Record => UnderlyingRecord}
import com.amazonaws.services.kinesis.model.{GetShardIteratorRequest => UnderlyingGetShardIteratorRequest}
import com.amazonaws.services.kinesis.model.{GetShardIteratorResult => UnderlyingGetShardIteratorResult}
import com.amazonaws.services.kinesis.model.{ListStreamsRequest => UnderlyingListStreamsRequest}
import com.amazonaws.services.kinesis.model.{ListStreamsResult => UnderlyingListStreamsResult}
import com.amazonaws.services.kinesis.model.{MergeShardsRequest => UnderlyingMergeShardsRequest}
import com.amazonaws.services.kinesis.model.{PutRecordRequest => UnderlyingPutRecordRequest}
import com.amazonaws.services.kinesis.model.{PutRecordResult => UnderlyingPutRecordResult}

import scala.collection.JavaConverters._

// TODO: More converters. Break out? Change package?
class ModelConverter {
  def toAws(createStreamRequest: CreateStreamRequest): UnderlyingCreateStreamRequest = {
    new UnderlyingCreateStreamRequest().
      withShardCount(createStreamRequest.shardCount).
      withStreamName(createStreamRequest.streamName)
  }

  def toAws(deleteStreamRequest: DeleteStreamRequest): UnderlyingDeleteStreamRequest = {
    new UnderlyingDeleteStreamRequest().
      withStreamName(deleteStreamRequest.streamName)
  }

  def toAws(request: DescribeStreamRequest): UnderlyingDescribeStreamRequest = {
    val underlying = new UnderlyingDescribeStreamRequest()
    underlying.setStreamName(request.streamName)
    request.exclusiveStartShardId.map(underlying.setExclusiveStartShardId)
    request.limit.map(new java.lang.Integer(_)).map(underlying.setLimit)
    underlying
  }

  def toAws(request: GetRecordsRequest): UnderlyingGetRecordsRequest = {
    val underlying = new UnderlyingGetRecordsRequest()
    underlying.setShardIterator(request.shardIterator)
    request.limit.map(new java.lang.Integer(_)).map(underlying.setLimit)
    underlying
  }

  def toAws(request: GetShardIteratorRequest): UnderlyingGetShardIteratorRequest = {
    val underlying = new UnderlyingGetShardIteratorRequest()
    underlying.setStreamName(request.streamName)
    underlying.setShardId(request.shardId)
    underlying.setShardIteratorType(ShardIteratorType.underlying(request.shardIteratorType))
    request.startingSequenceNumber.map(underlying.setStartingSequenceNumber)
    underlying
  }

  def toAws(request: ListStreamsRequest): UnderlyingListStreamsRequest = {
    val underlying = new UnderlyingListStreamsRequest()
    underlying.setExclusiveStartStreamName(request.exclusiveStartStreamName)
    request.limit.map(new java.lang.Integer(_)).map(underlying.setLimit)
    underlying
  }

  def toAws(request: MergeShardsRequest): UnderlyingMergeShardsRequest = {
    val underlying = new UnderlyingMergeShardsRequest()
    underlying.setStreamName(request.streamName)
    underlying.setShardToMerge(request.shardToMerge)
    underlying.setAdjacentShardToMerge(request.adjacentShardToMerge)
    underlying
  }

  def toAws(request: PutRecordRequest): UnderlyingPutRecordRequest = {
    val underlying = new UnderlyingPutRecordRequest()
    underlying.setStreamName(request.streamName)
    underlying.setPartitionKey(request.partitionKey)
    underlying.setData(request.data.toByteBuffer)
    request.explicitHashKey.map(underlying.setExplicitHashKey)
    request.sequenceNumberForOrdering.map(underlying.setSequenceNumberForOrdering)
    underlying
  }

  def fromAws(underlying: UnderlyingDescribeStreamResult): DescribeStreamResponse = {
    val description = underlying.getStreamDescription
    DescribeStreamResponse(
      description.getStreamName,
      description.getStreamARN,
      StreamStatus(description.getStreamStatus),
      description.getShards.asScala.toList.map(fromAws)
    )
  }

  def fromAws(underlying: UnderlyingShard): Shard = {
    Shard(
      underlying.getShardId,
      underlying.getAdjacentParentShardId,
      underlying.getParentShardId,
      fromAws(underlying.getHashKeyRange),
      fromAws(underlying.getSequenceNumberRange)
    )
  }

  def fromAws(underlying: UnderlyingHashKeyRange): HashKeyRange = {
    HashKeyRange(
      underlying.getStartingHashKey,
      underlying.getEndingHashKey
    )
  }

  def fromAws(underlying: UnderlyingSequenceNumberRange): SequenceNumberRange = {
    SequenceNumberRange(
      underlying.getStartingSequenceNumber,
      underlying.getEndingSequenceNumber
    )
  }

  def fromAws(underlying: UnderlyingGetRecordsResult): GetRecordsResponse = {
    GetRecordsResponse(
      underlying.getNextShardIterator,
      underlying.getRecords.asScala.toList.map(fromAws)
    )
  }

  def fromAws(underlying: UnderlyingRecord): Record = {
    import akka.util.ByteString
    Record(
      underlying.getPartitionKey,
      underlying.getSequenceNumber,
      ByteString(underlying.getData)
    )
  }

  def fromAws(underlying: UnderlyingGetShardIteratorResult): GetShardIteratorResponse = {
    GetShardIteratorResponse(underlying.getShardIterator)
  }

  def fromAws(underlying: UnderlyingListStreamsResult): ListStreamsResponse = {
    ListStreamsResponse(underlying.getHasMoreStreams, underlying.getStreamNames.asScala.toList)
  }

  def fromAws(underlying: UnderlyingPutRecordResult): PutRecordResponse = {
    PutRecordResponse(underlying.getShardId, underlying.getSequenceNumber)
  }
}
