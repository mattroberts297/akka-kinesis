package org.typetastic.aws.kinesis.model

import com.amazonaws.services.kinesis.model.{CreateStreamRequest => UnderlyingCreateStreamRequest}
import com.amazonaws.services.kinesis.model.{DeleteStreamRequest => UnderlyingDeleteStreamRequest}
import com.amazonaws.services.kinesis.model.{DescribeStreamRequest => UnderlyingDescribeStreamRequest}
import com.amazonaws.services.kinesis.model.{DescribeStreamResult => UnderlyingDescribeStreamResult}
import com.amazonaws.services.kinesis.model.{Shard => UnderlyingShard}
import com.amazonaws.services.kinesis.model.{HashKeyRange => UnderlyingHashKeyRange}
import com.amazonaws.services.kinesis.model.{SequenceNumberRange => UnderlyingSequenceNumberRange}

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
}
