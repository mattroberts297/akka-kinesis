package org.typetastic.aws.kinesis.converters

import java.nio.ByteBuffer

import akka.util.ByteString
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
import org.scalatest.{WordSpec, Matchers}
import org.typetastic.aws.kinesis.model._

import scala.collection.JavaConverters._

class ToAwsKinesisSpec extends WordSpec with Matchers {
  def name[T : Manifest]: String = implicitly[Manifest[T]].runtimeClass.getSimpleName
  val converter = new ToAwsKinesis {}
  import converter._

  s"${name[ToAwsKinesis]}::toAws(${name[CreateStreamRequest]})" should {
    s"return an initialised ${name[UnderlyingCreateStreamRequest]}" in new Context {
      val expected = new UnderlyingCreateStreamRequest().withStreamName(testStreamName).withShardCount(testShardCount)
      val input = CreateStreamRequest(testStreamName, testShardCount)
      val output = toAws(input)
      output should be (expected)
    }
  }

  s"${name[ToAwsKinesis]}::toAws(${name[DeleteStreamRequest]})" should {
    s"return an initialised ${name[UnderlyingDeleteStreamRequest]}" in new Context {
      val expected = new UnderlyingDeleteStreamRequest().withStreamName(testStreamName)
      val input = DeleteStreamRequest(testStreamName)
      val output = toAws(input)
      output should be (expected)
    }
  }

  s"${name[ToAwsKinesis]}::toAws(${name[DescribeStreamRequest]})" should {
    s"return an initialised ${name[UnderlyingDescribeStreamRequest]}" in new Context {
      val expected = new UnderlyingDescribeStreamRequest().
        withStreamName(testStreamName).
        withExclusiveStartShardId(testExclusiveStartShardId).
        withLimit(testLimit)
      val input = DescribeStreamRequest(testStreamName, Some(testExclusiveStartShardId), Some(testLimit))
      val output = toAws(input)
      output should be(expected)
    }
  }

  s"${name[ToAwsKinesis]}::toAws(${name[GetRecordsRequest]})" should {
    s"return an initialised ${name[UnderlyingGetRecordsRequest]}" in new Context {
      val expected = new UnderlyingGetRecordsRequest().withShardIterator(testShardIterator).withLimit(testLimit)
      val input = GetRecordsRequest(testShardIterator, Some(testLimit))
      val output = toAws(input)
      output should be (expected)
    }
  }

  s"${name[ToAwsKinesis]}::toAws(${name[GetShardIteratorRequest]})" should {
    s"return an initialised ${name[UnderlyingGetShardIteratorRequest]}" in new Context {
      val expected = new UnderlyingGetShardIteratorRequest().
        withStreamName(testStreamName).
        withShardId(testShardId).
        withShardIteratorType(testShardIteratorType).
        withStartingSequenceNumber(testStartingSequenceNumber)
      val input = GetShardIteratorRequest(
        testStreamName,
        testShardId,
        ShardIteratorType(testShardIteratorType),
        Some(testStartingSequenceNumber))
      val output = toAws(input)
      output should be (expected)
    }
  }

  s"${name[ToAwsKinesis]}::toAws(${name[ListStreamsRequest]})" should {
    s"return an initialised ${name[UnderlyingListStreamsRequest]}" in new Context {
      val expected = new UnderlyingListStreamsRequest().
        withExclusiveStartStreamName(testExclusiveStartStreamName).
        withLimit(testLimit)
      val input = ListStreamsRequest(Some(testExclusiveStartStreamName), Some(testLimit))
      val output = toAws(input)
      output should be (expected)
    }
  }

  s"${name[ToAwsKinesis]}::toAws(${name[MergeShardsRequest]})" should {
    s"return an initialised ${name[UnderlyingMergeShardsRequest]}" in new Context {
      val expected = new UnderlyingMergeShardsRequest().
        withStreamName(testStreamName).
        withShardToMerge(testShardToMerge).
        withAdjacentShardToMerge(testAdjacentShardToMerge)
      val input = MergeShardsRequest(testStreamName, testShardToMerge, testAdjacentShardToMerge)
      val output = toAws(input)
      output should be (expected)
    }
  }

  s"${name[ToAwsKinesis]}::toAws(${name[PutRecordRequest]})" should {
    s"return an initialised ${name[UnderlyingPutRecordRequest]}" in new Context {
      val expected = new UnderlyingPutRecordRequest().
        withStreamName(testStreamName).
        withPartitionKey(testPartitionKey).
        withData(testDataByteBuffer).
        withExplicitHashKey(testExplicitHashKey).
        withSequenceNumberForOrdering(testSequenceNumberForOrdering)
      val input = PutRecordRequest(
        testStreamName,
        testPartitionKey,
        testDataByteString,
        Some(testExplicitHashKey),
        Some(testSequenceNumberForOrdering))
      val output = toAws(input)
      output should be (expected)
    }
  }

  s"${name[ToAwsKinesis]}::toAws(${name[PutRecordsRequest]})" should {
    s"return an initialised ${name[UnderlyingPutRecordsRequest]}" in new Context {
      val expected = new UnderlyingPutRecordsRequest().withStreamName(testStreamName).withRecords(testRecordsUnderlying)
      val input = PutRecordsRequest(testStreamName, testRecords)
      val output = toAws(input)
      output should be (expected)
    }
  }

  s"${name[ToAwsKinesis]}::toAws(${name[SplitShardRequest]})" should {
    s"return an initialised ${name[UnderlyingSplitShardRequest]}" in new Context {
      val expected = new UnderlyingSplitShardRequest().
        withStreamName(testStreamName).
        withShardToSplit(testShardToSplit).
        withNewStartingHashKey(testNewStartingHashKey)
      val input = SplitShardRequest(testStreamName, testShardToSplit, testNewStartingHashKey)
      val output = toAws(input)
      output should be (expected)
    }
  }

  trait Context {
    val testStreamName = "TestStreamName"
    val testShardCount = 1
    val testLimit = 2
    val testExclusiveStartShardId = "TestExclusiveStartShardId"
    val testShardIterator = "TestShardIterator"
    val testShardId = "TestShardId"
    val testShardIteratorType = "AT_SEQUENCE_NUMBER"
    val testStartingSequenceNumber = "TestStartingSequenceNumber"
    val testExclusiveStartStreamName = "TestExclusiveStartStreamName"
    val testShardToMerge = "TestShardToMerge"
    val testAdjacentShardToMerge = "TestAdjacentShardToMerge"
    val testPartitionKey = "TestPartitionKey"
    val testDataBytes = "TestData".getBytes
    val testDataByteBuffer = ByteBuffer.wrap(testDataBytes)
    val testDataByteString = ByteString(testDataBytes)
    val testExplicitHashKey = "TestExplicitHashKey"
    val testSequenceNumberForOrdering = "TestSequenceNumberForOrdering"
    val testRecordsUnderlying = List(new UnderlyingPutRecordsRequestEntry().
      withData(testDataByteBuffer).
      withPartitionKey(testPartitionKey).
      withExplicitHashKey(testExplicitHashKey)).asJava
    val testRecords = List(PutRecordsRequestEntry(testPartitionKey, testDataByteString, Some(testExplicitHashKey)))
    val testShardToSplit = "TestShardToSplit"
    val testNewStartingHashKey = "TestNewStartingHashKey"
  }
}
