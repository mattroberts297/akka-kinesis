package org.typetastic.aws.kinesis.converters

import com.amazonaws.services.kinesis.model.{CreateStreamRequest => UnderlyingCreateStreamRequest}
import com.amazonaws.services.kinesis.model.{DeleteStreamRequest => UnderlyingDeleteStreamRequest}
import com.amazonaws.services.kinesis.model.{DescribeStreamRequest => UnderlyingDescribeStreamRequest}
import com.amazonaws.services.kinesis.model.{GetRecordsRequest => UnderlyingGetRecordsRequest}
import com.amazonaws.services.kinesis.model.{GetShardIteratorRequest => UnderlyingGetShardIteratorRequest}
import com.amazonaws.services.kinesis.model.{ListStreamsRequest => UnderlyingListStreamsRequest}
import com.amazonaws.services.kinesis.model.{MergeShardsRequest => UnderlyingMergeShardsRequest}
import com.amazonaws.services.kinesis.model.{PutRecordRequest => UnderlyingPutRecordRequest}
import com.amazonaws.services.kinesis.model.{PutRecordsRequest => UnderlyingPutRecordsRequest}
import com.amazonaws.services.kinesis.model.{PutRecordsRequestEntry => UnderlyingPutRecordsRequestEntry}
import com.amazonaws.services.kinesis.model.{SplitShardRequest => UnderlyingSplitShardRequest}
import org.typetastic.aws.kinesis.model._

import scala.collection.JavaConverters._

trait ToAwsKinesis {
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

  def toAws(request: PutRecordsRequest): UnderlyingPutRecordsRequest = {
    val underlying = new UnderlyingPutRecordsRequest()
    underlying.setStreamName(request.streamName)
    underlying.setRecords(request.records.map(toAws).asJava)
    underlying
  }

  def toAws(entry: PutRecordsRequestEntry): UnderlyingPutRecordsRequestEntry = {
    val underlying = new UnderlyingPutRecordsRequestEntry()
    underlying.setPartitionKey(entry.partitionKey)
    underlying.setData(entry.data.toByteBuffer)
    entry.explicitHashKey.map(underlying.setExplicitHashKey)
    underlying
  }

  def toAws(request: SplitShardRequest): UnderlyingSplitShardRequest = {
    val underlying = new UnderlyingSplitShardRequest()
    underlying.setStreamName(request.streamName)
    underlying.setShardToSplit(request.shardToSplit)
    underlying.setNewStartingHashKey(request.newStartingHashKey)
    underlying
  }
}
