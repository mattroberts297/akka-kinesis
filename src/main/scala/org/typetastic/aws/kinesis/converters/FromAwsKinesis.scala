package org.typetastic.aws.kinesis.converters

import com.amazonaws.services.kinesis.model.{DescribeStreamResult => UnderlyingDescribeStreamResult}
import com.amazonaws.services.kinesis.model.{Shard => UnderlyingShard}
import com.amazonaws.services.kinesis.model.{HashKeyRange => UnderlyingHashKeyRange}
import com.amazonaws.services.kinesis.model.{SequenceNumberRange => UnderlyingSequenceNumberRange}
import com.amazonaws.services.kinesis.model.{GetRecordsResult => UnderlyingGetRecordsResult}
import com.amazonaws.services.kinesis.model.{Record => UnderlyingRecord}
import com.amazonaws.services.kinesis.model.{GetShardIteratorResult => UnderlyingGetShardIteratorResult}
import com.amazonaws.services.kinesis.model.{ListStreamsResult => UnderlyingListStreamsResult}
import com.amazonaws.services.kinesis.model.{PutRecordResult => UnderlyingPutRecordResult}
import com.amazonaws.services.kinesis.model.{PutRecordsResult => UnderlyingPutRecordsResult}
import com.amazonaws.services.kinesis.model.{PutRecordsResultEntry => UnderlyingPutRecordsResultEntry}
import org.typetastic.aws.kinesis.model._

import scala.collection.JavaConverters._

trait FromAwsKinesis {
  def fromAws(underlying: UnderlyingDescribeStreamResult): DescribeStreamResponse = {
    val description = underlying.getStreamDescription
    DescribeStreamResponse(
      description.getStreamName,
      description.getStreamARN,
      StreamStatus(description.getStreamStatus),
      description.getShards.asScala.toList.map(fromAws),
      description.getHasMoreShards
    )
  }

  def fromAws(underlying: UnderlyingShard): Shard = {
    Shard(
      underlying.getShardId,
      underlying.getParentShardId,
      underlying.getAdjacentParentShardId,
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

  def fromAws(underlying: UnderlyingPutRecordsResult): PutRecordsResponse = {
    PutRecordsResponse(underlying.getFailedRecordCount, underlying.getRecords.asScala.toList.map(fromAws))
  }

  def fromAws(underlying: UnderlyingPutRecordsResultEntry): PutRecordsResponseEntry = {
    if (underlying.getErrorCode == null) {
      PutRecordsResponseSuccessEntry(underlying.getShardId, underlying.getSequenceNumber)
    } else {
      PutRecordsResponseFailureEntry(underlying.getErrorCode, underlying.getErrorMessage)
    }
  }
}
