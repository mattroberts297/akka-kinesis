package org.typetastic.aws.kinesis

import java.util.concurrent.{Future => JFuture}

import com.amazonaws.AmazonWebServiceRequest
import org.mockito.invocation.InvocationOnMock
import org.mockito.stubbing.Answer
import org.scalatest.mock.MockitoSugar
import org.scalatest.{WordSpec, Matchers}
import com.amazonaws.services.kinesis.{AmazonKinesisAsync => Underlying}
import org.typetastic.aws.handlers.{PromiseHandler, PromiseHandlerFactory}
import org.typetastic.aws.kinesis.converters.KinesisConverter
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

import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class KinesisClientSpec extends WordSpec with Matchers with MockitoSugar {
  val classUnderTest = classOf[KinesisClient].getSimpleName

  s"$classUnderTest::createStream" should {
    "invoke createStreamAsync" in new CreateStreamContext {
      // Arrange.
      when(mockConverter.toAws(mockRequest)).thenReturn(mockUnderlyingRequest)
      when(mockFactory.create[UnderlyingCreateStreamRequest, Void]()).thenReturn(handler)
      when(mockUnderlying.createStreamAsync(mockUnderlyingRequest, handler)).then(invokeHandler)
      // Act.
      val resultFuture = client.createStream(mockRequest)
      val result = Await.result(resultFuture, timeout)
      // Assert.
      verify(mockConverter).toAws(mockRequest)
      verify(mockUnderlying).createStreamAsync(mockUnderlyingRequest, handler)
    }
  }

  s"$classUnderTest::deleteStream" should {
    "invoke deleteStreamAsync" in new DeleteStreamContext {
      // Arrange.
      when(mockConverter.toAws(mockRequest)).thenReturn(mockUnderlyingRequest)
      when(mockFactory.create[UnderlyingDeleteStreamRequest, Void]()).thenReturn(handler)
      when(mockUnderlying.deleteStreamAsync(mockUnderlyingRequest, handler)).then(invokeHandler)
      // Act.
      val resultFuture = client.deleteStream(mockRequest)
      val result = Await.result(resultFuture, timeout)
      // Assert.
      verify(mockConverter).toAws(mockRequest)
      verify(mockUnderlying).deleteStreamAsync(mockUnderlyingRequest, handler)
    }
  }

  s"$classUnderTest::describeStream" should {
    "invoke describeStreamAsync" in new DescribeStreamContext {
      // Arrange.
      when(mockConverter.toAws(mockRequest)).thenReturn(mockUnderlyingRequest)
      when(mockFactory.create[UnderlyingDescribeStreamRequest, UnderlyingDescribeStreamResult]()).thenReturn(handler)
      when(mockUnderlying.describeStreamAsync(mockUnderlyingRequest, handler)).then(invokeHandler)
      when(mockConverter.fromAws(mockUnderlyingResponse)).thenReturn(mockResponse)
      // Act.
      val resultFuture = client.describeStream(mockRequest)
      val result = Await.result(resultFuture, timeout)
      // Assert.
      verify(mockConverter).toAws(mockRequest)
      verify(mockUnderlying).describeStreamAsync(mockUnderlyingRequest, handler)
      verify(mockConverter).fromAws(mockUnderlyingResponse)
      result should be (mockResponse)
    }
  }

  s"$classUnderTest::getRecords" should {
    "invoke getRecordsAsync" in new GetRecordsContext {
      // Arrange.
      when(mockConverter.toAws(mockRequest)).thenReturn(mockUnderlyingRequest)
      when(mockFactory.create[UnderlyingGetRecordsRequest, UnderlyingGetRecordsResult]()).thenReturn(mockHandler)
      when(mockHandler.future).thenReturn(mockFuture)
      // Act.
      client.getRecords(mockRequest)
      // Assert.
      verify(mockConverter).toAws(mockRequest)
      verify(mockUnderlying).getRecordsAsync(mockUnderlyingRequest, mockHandler)
    }
  }

  s"$classUnderTest::getShardIterator" should {
    "invoke getShardIteratorAsync" in new GetShardIteratorContext {
      // Arrange.
      when(mockConverter.toAws(mockRequest)).thenReturn(mockUnderlyingRequest)
      when(mockFactory.create[UnderlyingGetShardIteratorRequest, UnderlyingGetShardIteratorResult]()).thenReturn(mockHandler)
      when(mockHandler.future).thenReturn(mockFuture)
      // Act.
      client.getShardIterator(mockRequest)
      // Assert.
      verify(mockConverter).toAws(mockRequest)
      verify(mockUnderlying).getShardIteratorAsync(mockUnderlyingRequest, mockHandler)
    }
  }

  s"$classUnderTest::listStreams" should {
    "invoke listStreamsAsync" in new ListStreamsContext {
      // Arrange.
      when(mockConverter.toAws(mockRequest)).thenReturn(mockUnderlyingRequest)
      when(mockFactory.create[UnderlyingListStreamsRequest, UnderlyingListStreamsResult]()).thenReturn(mockHandler)
      when(mockHandler.future).thenReturn(mockFuture)
      // Act.
      client.listStreams(mockRequest)
      // Assert.
      verify(mockConverter).toAws(mockRequest)
      verify(mockUnderlying).listStreamsAsync(mockUnderlyingRequest, mockHandler)
    }
  }

  s"$classUnderTest::mergeShards" should {
    "invoke mergeShardsAsync" in new MergeShardsContext {
      // Arrange.
      when(mockConverter.toAws(mockRequest)).thenReturn(mockUnderlyingRequest)
      when(mockFactory.create[UnderlyingMergeShardsRequest, Void]()).thenReturn(mockHandler)
      when(mockHandler.future).thenReturn(mockFuture)
      // Act.
      client.mergeShards(mockRequest)
      // Assert.
      verify(mockConverter).toAws(mockRequest)
      verify(mockUnderlying).mergeShardsAsync(mockUnderlyingRequest, mockHandler)
    }
  }

  s"$classUnderTest::putRecord" should {
    "invoke putRecordAsync" in new PutRecordContext {
      // Arrange.
      when(mockConverter.toAws(mockRequest)).thenReturn(mockUnderlyingRequest)
      when(mockFactory.create[UnderlyingPutRecordRequest, UnderlyingPutRecordResult]()).thenReturn(mockHandler)
      when(mockHandler.future).thenReturn(mockFuture)
      // Act.
      client.putRecord(mockRequest)
      // Assert.
      verify(mockConverter).toAws(mockRequest)
      verify(mockUnderlying).putRecordAsync(mockUnderlyingRequest, mockHandler)
    }
  }

  s"$classUnderTest::putRecords" should {
    "invoke putRecordsAsync" in new PutRecordsContext {
      // Arrange.
      when(mockConverter.toAws(mockRequest)).thenReturn(mockUnderlyingRequest)
      when(mockFactory.create[UnderlyingPutRecordsRequest, UnderlyingPutRecordsResult]()).thenReturn(mockHandler)
      when(mockHandler.future).thenReturn(mockFuture)
      // Act.
      client.putRecords(mockRequest)
      // Assert.
      verify(mockConverter).toAws(mockRequest)
      verify(mockUnderlying).putRecordsAsync(mockUnderlyingRequest, mockHandler)
    }
  }

  s"$classUnderTest::splitShard" should {
    "invoke splitShardAsync" in new SplitShardContext {
      // Arrange.
      when(mockConverter.toAws(mockRequest)).thenReturn(mockUnderlyingRequest)
      when(mockFactory.create[UnderlyingSplitShardRequest, Void]()).thenReturn(mockHandler)
      when(mockHandler.future).thenReturn(mockFuture)
      // Act.
      client.splitShard(mockRequest)
      // Assert.
      verify(mockConverter).toAws(mockRequest)
      verify(mockUnderlying).splitShardAsync(mockUnderlyingRequest, mockHandler)
    }
  }

  trait Context {
    val mockUnderlying = mock[Underlying]
    val mockConverter = mock[KinesisConverter]
    val mockFactory = mock[PromiseHandlerFactory]
    val client = new KinesisClient(mockUnderlying, mockConverter, mockFactory)
    val timeout = 500.milliseconds
  }

  class PartiallyTypedContext[
      Request <: AnyRef : Manifest,
      UnderlyingRequest <: AmazonWebServiceRequest : Manifest,
      UnderlyingResponse : Manifest] extends Context {
    val mockJavaFuture = mock[JFuture[UnderlyingResponse]]
    val mockRequest = mock[Request]
    val mockUnderlyingRequest = mock[UnderlyingRequest]
    val mockHandler = mock[PromiseHandler[UnderlyingRequest, UnderlyingResponse]]
    val handler = PromiseHandler[UnderlyingRequest, UnderlyingResponse]()
    val mockFuture = mock[Future[UnderlyingResponse]]
  }

  class FullyTypedContext[
      Request <: AnyRef : Manifest,
      Response <: AnyRef : Manifest,
      UnderlyingRequest <: AmazonWebServiceRequest : Manifest,
      UnderlyingResponse <: AnyRef : Manifest] extends PartiallyTypedContext[
      Request,
      UnderlyingRequest,
      UnderlyingResponse] {
    val mockResponse = mock[Response]
    val mockUnderlyingResponse = mock[UnderlyingResponse]
    def invokeHandler = new Answer[JFuture[UnderlyingResponse]] {
      override def answer(invocation: InvocationOnMock): JFuture[UnderlyingResponse] = {
        handler.onSuccess(mockUnderlyingRequest, mockUnderlyingResponse)
        mockJavaFuture
      }
    }
  }

  class CreateStreamContext extends PartiallyTypedContext[CreateStreamRequest, UnderlyingCreateStreamRequest, Void] {
    def invokeHandler = new Answer[JFuture[Void]] {
      override def answer(invocation: InvocationOnMock): JFuture[Void] = {
        handler.onSuccess(mockUnderlyingRequest, null)
        mockJavaFuture
      }
    }
  }

  class DeleteStreamContext extends PartiallyTypedContext[DeleteStreamRequest, UnderlyingDeleteStreamRequest, Void] {
    def invokeHandler = new Answer[JFuture[Void]] {
      override def answer(invocation: InvocationOnMock): JFuture[Void] = {
        handler.onSuccess(mockUnderlyingRequest, null)
        mockJavaFuture
      }
    }
  }

  class DescribeStreamContext extends FullyTypedContext[
    DescribeStreamRequest,
    DescribeStreamResponse,
    UnderlyingDescribeStreamRequest,
    UnderlyingDescribeStreamResult]

  class GetRecordsContext extends PartiallyTypedContext[
    GetRecordsRequest,
    UnderlyingGetRecordsRequest,
    UnderlyingGetRecordsResult]

  class GetShardIteratorContext extends PartiallyTypedContext[
    GetShardIteratorRequest,
    UnderlyingGetShardIteratorRequest,
    UnderlyingGetShardIteratorResult]

  class ListStreamsContext extends PartiallyTypedContext[
    ListStreamsRequest,
    UnderlyingListStreamsRequest,
    UnderlyingListStreamsResult]

  class MergeShardsContext extends PartiallyTypedContext[MergeShardsRequest, UnderlyingMergeShardsRequest, Void]

  class PutRecordContext extends PartiallyTypedContext[
    PutRecordRequest,
    UnderlyingPutRecordRequest,
    UnderlyingPutRecordResult]

  class PutRecordsContext extends PartiallyTypedContext[
    PutRecordsRequest,
    UnderlyingPutRecordsRequest,
    UnderlyingPutRecordsResult]

  class SplitShardContext extends PartiallyTypedContext[SplitShardRequest, UnderlyingSplitShardRequest, Void]
}
