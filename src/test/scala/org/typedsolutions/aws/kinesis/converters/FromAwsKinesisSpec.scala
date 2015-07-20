package org.typedsolutions.aws.kinesis.converters

import java.nio.ByteBuffer

import akka.util.ByteString
import com.amazonaws.services.kinesis.model.{DescribeStreamResult => UnderlyingDescribeStreamResult}
import com.amazonaws.services.kinesis.model.{StreamDescription => UnderlyingStreamDescription}
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
import org.scalatest.{WordSpec, Matchers}
import org.typedsolutions.aws.kinesis.model._

class FromAwsKinesisSpec extends WordSpec with Matchers {
  def name[T : Manifest]: String = implicitly[Manifest[T]].runtimeClass.getSimpleName
  val converter = new FromAwsKinesis {}
  import converter._

  s"${name[FromAwsKinesis]}::toAws(${name[UnderlyingDescribeStreamResult]})" should {
    s"return an initialised ${name[DescribeStreamResponse]}" in new DescribeStreamContext {
      val expected = testDescribeStreamResponse
      val input = testUnderlyingDescribeStreamResult
      val output = fromAws(input)
      output should be (expected)
    }
  }

  s"${name[FromAwsKinesis]}::toAws(${name[UnderlyingGetRecordsResult]})" should {
    s"return an initialised ${name[GetRecordsResponse]}" in new GetRecordsContext {
      val expected = testGetRecordsResponse
      val input = testUnderlyingGetRecordsResult
      val output = fromAws(input)
      output should be (expected)
    }
  }

  s"${name[FromAwsKinesis]}::toAws(${name[UnderlyingGetShardIteratorResult]})" should {
    s"return an initialised ${name[GetShardIteratorResponse]}" in new GetShardIteratorContext {
      val expected = testGetShardIteratorResponse
      val input = testUnderlyingGetShardIteratorResult
      val output = fromAws(input)
      output should be (expected)
    }
  }

  s"${name[FromAwsKinesis]}::toAws(${name[UnderlyingListStreamsResult]})" should {
    s"return an initialised ${name[ListStreamsResponse]}" in new ListStreamsContext {
      val expected = testListStreamsResponse
      val input = testUnderlyingListStreamsResult
      val output = fromAws(input)
      output should be (expected)
    }
  }

  s"${name[FromAwsKinesis]}::toAws(${name[UnderlyingPutRecordResult]})" should {
    s"return an initialised ${name[PutRecordResponse]}" in new PutRecordContext {
      val expected = putRecordResponse
      val input = underlyingPutRecordResult
      val output = fromAws(input)
      output should be (expected)
    }
  }

  s"${name[FromAwsKinesis]}::toAws(${name[UnderlyingPutRecordsResult]})" should {
    s"return an initialised ${name[PutRecordsResponse]}" in new PutRecordsContext {
      val expected = putRecordsResponse
      val input = underlyingPutRecordsResult
      val output = fromAws(input)
      output should be (expected)
    }
  }

  trait Context {
  }

  trait DescribeStreamContext extends Context {
    val testStreamName = "TestStreamName"
    val testStreamArn = "TestStreamArn"
    val testStreamStatusString = "ACTIVE"
    val testStreamStatus = StreamStatus(testStreamStatusString)
    val testHasMoreShards = true
    val testId = "TestId"
    val testAdjacentParentId = "TestAdjacentParentId"
    val testParentId = "TestParentId"
    val testStartingHashKey = "TestStartingHashKey"
    val testEndingHashKey = "TestEndingHashKey"
    val testStartingSequenceNumber = "TestStartingSequenceNumber"
    val testEndingSequenceNumber = "TestEndingSequenceNumber"
    val testShards = List(Shard(
      testId,
      testParentId,
      testAdjacentParentId,
      HashKeyRange(testStartingHashKey, testEndingHashKey),
      SequenceNumberRange(testStartingSequenceNumber, testEndingSequenceNumber)))
    val testDescribeStreamResponse = DescribeStreamResponse(
      testStreamName,
      testStreamArn,
      testStreamStatus,
      testShards,
      testHasMoreShards)
    val testUnderlyingDescribeStreamResult = new UnderlyingDescribeStreamResult().
      withStreamDescription(new UnderlyingStreamDescription().
        withStreamName(testStreamName).
        withStreamARN(testStreamArn).
        withStreamStatus(testStreamStatusString).
        withShards(new UnderlyingShard().
          withShardId(testId).
          withParentShardId(testParentId).
          withAdjacentParentShardId(testAdjacentParentId).
          withHashKeyRange(new UnderlyingHashKeyRange().
            withStartingHashKey(testStartingHashKey).
            withEndingHashKey(testEndingHashKey)).
          withSequenceNumberRange(new UnderlyingSequenceNumberRange().
            withStartingSequenceNumber(testStartingSequenceNumber).
            withEndingSequenceNumber(testEndingSequenceNumber))).
        withHasMoreShards(testHasMoreShards))
  }

  trait GetRecordsContext extends Context {
    val testNextShardIterator = "TestNextShardIterator"
    val testPartitionKey = "TestPartitionKey"
    val testSequenceNumber = "TestSequenceNumber"
    val testDataBytes = "TestData".getBytes
    val testDataByteBuffer = ByteBuffer.wrap(testDataBytes)
    val testDataByteString = ByteString(testDataBytes)
    val testUnderlyingGetRecordsResult = new UnderlyingGetRecordsResult().
      withNextShardIterator(testNextShardIterator).
      withRecords(new UnderlyingRecord().
        withPartitionKey(testPartitionKey).
        withSequenceNumber(testSequenceNumber).
        withData(testDataByteBuffer))
    val testGetRecordsResponse = GetRecordsResponse(testNextShardIterator, List(Record(
      testPartitionKey,
      testSequenceNumber,
      testDataByteString)))
  }

  trait GetShardIteratorContext extends Context {
    val testShardIterator = "TestShardIterator"
    val testGetShardIteratorResponse = GetShardIteratorResponse(testShardIterator)
    val testUnderlyingGetShardIteratorResult = new UnderlyingGetShardIteratorResult().
      withShardIterator(testShardIterator)
  }

  trait ListStreamsContext extends Context {
    val testHasMoreStreams = true
    val testStreamName = "TestStreamName"
    val testStreamNames = List(testStreamName)
    val testListStreamsResponse = ListStreamsResponse(testHasMoreStreams, testStreamNames)
    val testUnderlyingListStreamsResult = new UnderlyingListStreamsResult().
      withHasMoreStreams(testHasMoreStreams).
      withStreamNames(testStreamName)
  }

  trait PutRecordContext extends Context {
    val testShardId = "TestShardId"
    val testSequenceNumber = "TestSequenceNumber"
    val putRecordResponse = PutRecordResponse(testShardId, testSequenceNumber)
    val underlyingPutRecordResult = new UnderlyingPutRecordResult().
      withShardId(testShardId).
      withSequenceNumber(testSequenceNumber)
  }

  trait PutRecordsContext extends Context {
    val testFailedRecordCount = 0
    val testShardId = "TestShardId"
    val testSequenceNumber = "TestSequenceNuber"
    val errorCode = "ProvisionedThroughputExceededException"
    val errorMessage = "InternalFailure"
    val successUnderlyingPutRecordsResultEntry = new UnderlyingPutRecordsResultEntry().
      withShardId(testShardId).
      withSequenceNumber(testSequenceNumber)
    val failureUnderlyingPutRecordsResultEntry = new UnderlyingPutRecordsResultEntry().
      withErrorCode(errorCode).
      withErrorMessage(errorMessage)
    val putRecordsResponse = PutRecordsResponse(
      testFailedRecordCount,
      List(
        PutRecordsResponseSuccessEntry(testShardId, testSequenceNumber),
        PutRecordsResponseFailureEntry(errorCode, errorMessage)))
    val underlyingPutRecordsResult = new UnderlyingPutRecordsResult().
      withFailedRecordCount(testFailedRecordCount).
      withRecords(successUnderlyingPutRecordsResultEntry, failureUnderlyingPutRecordsResultEntry)
  }
}
