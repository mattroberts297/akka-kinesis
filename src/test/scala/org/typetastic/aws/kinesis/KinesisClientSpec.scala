package org.typetastic.aws.kinesis

import com.amazonaws.AmazonWebServiceRequest
import org.scalatest.mock.MockitoSugar
import org.scalatest.{WordSpec, Matchers}
import com.amazonaws.services.kinesis.{AmazonKinesisAsync => Underlying}
import org.typetastic.aws.handlers.{PromiseHandler, PromiseHandlerFactory}
import org.typetastic.aws.kinesis.model._
import org.mockito.Mockito._
import org.mockito.Matchers._
import com.amazonaws.services.kinesis.model.{CreateStreamRequest => UnderlyingCreateStreamRequest}
import com.amazonaws.services.kinesis.model.{DeleteStreamRequest => UnderlyingDeleteStreamRequest}
import com.amazonaws.services.kinesis.model.{DescribeStreamRequest => UnderlyingDescribeStreamRequest}
import com.amazonaws.services.kinesis.model.{DescribeStreamResult => UnderlyingDescribeStreamResult}
import com.amazonaws.services.kinesis.model.{GetRecordsRequest => UnderlyingGetRecordsRequest}
import com.amazonaws.services.kinesis.model.{GetRecordsResult => UnderlyingGetRecordsResult}
import com.amazonaws.services.kinesis.model.{GetShardIteratorRequest => UnderlyingGetShardIteratorRequest}
import com.amazonaws.services.kinesis.model.{GetShardIteratorResult => UnderlyingGetShardIteratorResult}
import com.amazonaws.services.kinesis.model.{ListStreamsRequest => UnderlyingListStreamsRequest}
import com.amazonaws.services.kinesis.model.{ListStreamsResult => UnderlyingListStreamsResult}
import com.amazonaws.services.kinesis.model.{MergeShardsRequest => UnderlyingMergeShardsRequest}
import com.amazonaws.services.kinesis.model.{PutRecordRequest => UnderlyingPutRecordRequest}
import com.amazonaws.services.kinesis.model.{PutRecordResult => UnderlyingPutRecordResult}
import com.amazonaws.services.kinesis.model.{PutRecordsRequest => UnderlyingPutRecordsRequest}
import com.amazonaws.services.kinesis.model.{PutRecordsResult => UnderlyingPutRecordsResult}
import com.amazonaws.services.kinesis.model.{SplitShardRequest => UnderlyingSplitShardRequest}

import scala.concurrent.Promise

class KinesisClientSpec extends WordSpec with Matchers with MockitoSugar {
  val classUnderTest = classOf[KinesisClient].getSimpleName

  s"$classUnderTest::createStream" should {
    "invoke createStreamAsync" in new CreateStreamContext {
      // Arrange.
      when(converter.toAws(request)).thenReturn(underlyingRequest)
      when(factory.create[UnderlyingCreateStreamRequest, Void](any[Promise[Void]])).thenReturn(handler)
      // Act.
      client.createStream(request)
      // Assert.
      verify(converter).toAws(request)
      verify(underlying).createStreamAsync(underlyingRequest, handler)
    }
  }

  s"$classUnderTest::deleteStream" should {
    "invoke deleteStreamAsync" in new DeleteStreamContext {
      // Arrange.
      when(converter.toAws(request)).thenReturn(underlyingRequest)
      when(factory.create[UnderlyingDeleteStreamRequest, Void](any[Promise[Void]])).thenReturn(handler)
      // Act.
      client.deleteStream(request)
      // Assert.
      verify(converter).toAws(request)
      verify(underlying).deleteStreamAsync(underlyingRequest, handler)
    }
  }

  s"$classUnderTest::describeStream" should {
    "invoke describeStreamAsync" in new DescribeStreamContext {
      // Arrange.
      when(converter.toAws(request)).thenReturn(underlyingRequest)
      when(factory.create[
        UnderlyingDescribeStreamRequest,
        UnderlyingDescribeStreamResult](
        any[Promise[UnderlyingDescribeStreamResult]])).thenReturn(handler)
      // Act.
      client.describeStream(request)
      // Assert.
      verify(converter).toAws(request)
      verify(underlying).describeStreamAsync(underlyingRequest, handler)
    }
  }

  s"$classUnderTest::getRecords" should {
    "invoke getRecordsAsync" in new GetRecordsContext {
      // Arrange.
      when(converter.toAws(request)).thenReturn(underlyingRequest)
      when(factory.create[
        UnderlyingGetRecordsRequest,
        UnderlyingGetRecordsResult](
        any[Promise[UnderlyingGetRecordsResult]])).thenReturn(handler)
      // Act.
      client.getRecords(request)
      // Assert.
      verify(converter).toAws(request)
      verify(underlying).getRecordsAsync(underlyingRequest, handler)
    }
  }

  s"$classUnderTest::getShardIterator" should {
    "invoke getShardIteratorAsync" in new GetShardIteratorContext {
      // Arrange.
      when(converter.toAws(request)).thenReturn(underlyingRequest)
      when(factory.create[
        UnderlyingGetShardIteratorRequest,
        UnderlyingGetShardIteratorResult](
          any[Promise[UnderlyingGetShardIteratorResult]])).thenReturn(handler)
      // Act.
      client.getShardIterator(request)
      // Assert.
      verify(converter).toAws(request)
      verify(underlying).getShardIteratorAsync(underlyingRequest, handler)
    }
  }

  s"$classUnderTest::listStreams" should {
    "invoke listStreamsAsync" in new ListStreamsContext {
      // Arrange.
      when(converter.toAws(request)).thenReturn(underlyingRequest)
      when(factory.create[
        UnderlyingListStreamsRequest,
        UnderlyingListStreamsResult](
          any[Promise[UnderlyingListStreamsResult]])).thenReturn(handler)
      // Act.
      client.listStreams(request)
      // Assert.
      verify(converter).toAws(request)
      verify(underlying).listStreamsAsync(underlyingRequest, handler)
    }
  }

  s"$classUnderTest::mergeShards" should {
    "invoke mergeShardsAsync" in new MergeShardsContext {
      // Arrange.
      when(converter.toAws(request)).thenReturn(underlyingRequest)
      when(factory.create[
        UnderlyingMergeShardsRequest,
        Void](
          any[Promise[Void]])).thenReturn(handler)
      // Act.
      client.mergeShards(request)
      // Assert.
      verify(converter).toAws(request)
      verify(underlying).mergeShardsAsync(underlyingRequest, handler)
    }
  }

  s"$classUnderTest::putRecord" should {
    "invoke putRecordAsync" in new PutRecordContext {
      // Arrange.
      when(converter.toAws(request)).thenReturn(underlyingRequest)
      when(factory.create[
        UnderlyingPutRecordRequest,
        UnderlyingPutRecordResult](
          any[Promise[UnderlyingPutRecordResult]])).thenReturn(handler)
      // Act.
      client.putRecord(request)
      // Assert.
      verify(converter).toAws(request)
      verify(underlying).putRecordAsync(underlyingRequest, handler)
    }
  }

  s"$classUnderTest::putRecords" should {
    "invoke putRecordsAsync" in new PutRecordsContext {
      // Arrange.
      when(converter.toAws(request)).thenReturn(underlyingRequest)
      when(factory.create[
        UnderlyingPutRecordsRequest,
        UnderlyingPutRecordsResult](
          any[Promise[UnderlyingPutRecordsResult]])).thenReturn(handler)
      // Act.
      client.putRecords(request)
      // Assert.
      verify(converter).toAws(request)
      verify(underlying).putRecordsAsync(underlyingRequest, handler)
    }
  }

  s"$classUnderTest::splitShard" should {
    "invoke splitShardAsync" in new SplitShardContext {
      // Arrange.
      when(converter.toAws(request)).thenReturn(underlyingRequest)
      when(factory.create[
        UnderlyingSplitShardRequest,
        Void](
          any[Promise[Void]])).thenReturn(handler)
      // Act.
      client.splitShard(request)
      // Assert.
      verify(converter).toAws(request)
      verify(underlying).splitShardAsync(underlyingRequest, handler)
    }
  }

  trait Context {
    val underlying = mock[Underlying]
    val converter = mock[ModelConverter]
    val factory = mock[PromiseHandlerFactory]

    import scala.concurrent.ExecutionContext.Implicits.global
    val client = new KinesisClient(underlying, converter, factory)
  }

  class TypedContext[
      Request <: AnyRef : Manifest,
      UnderlyingRequest <: AmazonWebServiceRequest : Manifest,
      UnderlyingResponse : Manifest] extends Context {
    val request = mock[Request]
    val underlyingRequest = mock[UnderlyingRequest]
    val handler = mock[PromiseHandler[UnderlyingRequest, UnderlyingResponse]]
  }

  class CreateStreamContext extends TypedContext[CreateStreamRequest, UnderlyingCreateStreamRequest, Void]

  class DeleteStreamContext extends TypedContext[DeleteStreamRequest, UnderlyingDeleteStreamRequest, Void]

  class DescribeStreamContext extends TypedContext[
    DescribeStreamRequest,
    UnderlyingDescribeStreamRequest,
    UnderlyingDescribeStreamResult]

  class GetRecordsContext extends TypedContext[
    GetRecordsRequest,
    UnderlyingGetRecordsRequest,
    UnderlyingGetRecordsResult]

  class GetShardIteratorContext extends TypedContext[
    GetShardIteratorRequest,
    UnderlyingGetShardIteratorRequest,
    UnderlyingGetShardIteratorResult]

  class ListStreamsContext extends TypedContext[
    ListStreamsRequest,
    UnderlyingListStreamsRequest,
    UnderlyingListStreamsResult]

  class MergeShardsContext extends TypedContext[MergeShardsRequest, UnderlyingMergeShardsRequest, Void]

  class PutRecordContext extends TypedContext[
    PutRecordRequest,
    UnderlyingPutRecordRequest,
    UnderlyingPutRecordResult]

  class PutRecordsContext extends TypedContext[
    PutRecordsRequest,
    UnderlyingPutRecordsRequest,
    UnderlyingPutRecordsResult]

  class SplitShardContext extends TypedContext[SplitShardRequest, UnderlyingSplitShardRequest, Void]
}
