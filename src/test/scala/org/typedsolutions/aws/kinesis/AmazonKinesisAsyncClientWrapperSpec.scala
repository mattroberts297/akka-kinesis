package org.typedsolutions.aws.kinesis

import java.util.concurrent.{Future => JFuture}

import com.amazonaws.AmazonWebServiceRequest
import org.mockito.invocation.InvocationOnMock
import org.mockito.stubbing.Answer
import org.scalatest.mock.MockitoSugar
import org.scalatest.{WordSpec, Matchers}
import com.amazonaws.services.kinesis.{AmazonKinesisAsync => Underlying}
import org.typedsolutions.aws.handlers.{PromiseHandler, PromiseHandlerFactory}
import org.typedsolutions.aws.kinesis.converters.KinesisConverter
import org.typedsolutions.aws.kinesis.model._
import org.mockito.Mockito._
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

class AmazonKinesisAsyncClientWrapperSpec extends WordSpec with Matchers with MockitoSugar {
  val classUnderTest = classOf[AmazonKinesisAsyncClientWrapper].getSimpleName

  s"$classUnderTest::createStream" should {
    "invoke createStreamAsync" in new CreateStreamContext {
      // Arrange.
      when(mockConverter.toAws(mockRequest)).thenReturn(mockUnderlyingRequest)
      when(mockFactory.create[UnderlyingCreateStreamRequest, Void]()).thenReturn(handler)
      when(mockUnderlying.createStreamAsync(mockUnderlyingRequest, handler)).thenAnswer(invokeHandler)
      // Act.
      val resultFuture = client.createStream(mockRequest)
      val result = Await.result(resultFuture, timeout)
      // Assert.
      verify(mockConverter).toAws(mockRequest)
      verify(mockUnderlying).createStreamAsync(mockUnderlyingRequest, handler)
      result should be (CreateStreamResponse())
    }
  }

  s"$classUnderTest::deleteStream" should {
    "invoke deleteStreamAsync" in new DeleteStreamContext {
      // Arrange.
      when(mockConverter.toAws(mockRequest)).thenReturn(mockUnderlyingRequest)
      when(mockFactory.create[UnderlyingDeleteStreamRequest, Void]()).thenReturn(handler)
      when(mockUnderlying.deleteStreamAsync(mockUnderlyingRequest, handler)).thenAnswer(invokeHandler)
      // Act.
      val resultFuture = client.deleteStream(mockRequest)
      val result = Await.result(resultFuture, timeout)
      // Assert.
      verify(mockConverter).toAws(mockRequest)
      verify(mockUnderlying).deleteStreamAsync(mockUnderlyingRequest, handler)
      result should be (DeleteStreamResponse())
    }
  }

  s"$classUnderTest::describeStream" should {
    "invoke describeStreamAsync" in new DescribeStreamContext {
      // Arrange.
      when(mockConverter.toAws(mockRequest)).thenReturn(mockUnderlyingRequest)
      when(mockFactory.create[UnderlyingDescribeStreamRequest, UnderlyingDescribeStreamResult]()).thenReturn(handler)
      when(mockUnderlying.describeStreamAsync(mockUnderlyingRequest, handler)).thenAnswer(invokeHandler)
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
      when(mockFactory.create[UnderlyingGetRecordsRequest, UnderlyingGetRecordsResult]()).thenReturn(handler)
      when(mockUnderlying.getRecordsAsync(mockUnderlyingRequest, handler)).thenAnswer(invokeHandler)
      when(mockConverter.fromAws(mockUnderlyingResponse)).thenReturn(mockResponse)
      // Act.
      val resultFuture = client.getRecords(mockRequest)
      val result = Await.result(resultFuture, timeout)
      // Assert.
      verify(mockConverter).toAws(mockRequest)
      verify(mockUnderlying).getRecordsAsync(mockUnderlyingRequest, handler)
      verify(mockConverter).fromAws(mockUnderlyingResponse)
      result should be (mockResponse)
    }
  }

  s"$classUnderTest::getShardIterator" should {
    "invoke getShardIteratorAsync" in new GetShardIteratorContext {
      // Arrange.
      when(mockConverter.toAws(mockRequest)).thenReturn(mockUnderlyingRequest)
      when(mockFactory.create[UnderlyingGetShardIteratorRequest, UnderlyingGetShardIteratorResult]()).thenReturn(handler)
      when(mockUnderlying.getShardIteratorAsync(mockUnderlyingRequest, handler)).thenAnswer(invokeHandler)
      when(mockConverter.fromAws(mockUnderlyingResponse)).thenReturn(mockResponse)
      // Act.
      val resultFuture = client.getShardIterator(mockRequest)
      val result = Await.result(resultFuture, timeout)
      // Assert.
      verify(mockConverter).toAws(mockRequest)
      verify(mockUnderlying).getShardIteratorAsync(mockUnderlyingRequest, handler)
      verify(mockConverter).fromAws(mockUnderlyingResponse)
      result should be (mockResponse)
    }
  }

  s"$classUnderTest::listStreams" should {
    "invoke listStreamsAsync" in new ListStreamsContext {
      // Arrange.
      when(mockConverter.toAws(mockRequest)).thenReturn(mockUnderlyingRequest)
      when(mockFactory.create[UnderlyingListStreamsRequest, UnderlyingListStreamsResult]()).thenReturn(handler)
      when(mockUnderlying.listStreamsAsync(mockUnderlyingRequest, handler)).thenAnswer(invokeHandler)
      when(mockConverter.fromAws(mockUnderlyingResponse)).thenReturn(mockResponse)
      // Act.
      client.listStreams(mockRequest)
      // Assert.
      verify(mockConverter).toAws(mockRequest)
      verify(mockUnderlying).listStreamsAsync(mockUnderlyingRequest, handler)
    }
  }

  s"$classUnderTest::mergeShards" should {
    "invoke mergeShardsAsync" in new MergeShardsContext {
      // Arrange.
      when(mockConverter.toAws(mockRequest)).thenReturn(mockUnderlyingRequest)
      when(mockFactory.create[UnderlyingMergeShardsRequest, Void]()).thenReturn(handler)
      when(mockUnderlying.mergeShardsAsync(mockUnderlyingRequest, handler)).thenAnswer(invokeHandler)
      // Act.
      client.mergeShards(mockRequest)
      // Assert.
      verify(mockConverter).toAws(mockRequest)
      verify(mockUnderlying).mergeShardsAsync(mockUnderlyingRequest, handler)
    }
  }

  s"$classUnderTest::putRecord" should {
    "invoke putRecordAsync" in new PutRecordContext {
      // Arrange.
      when(mockConverter.toAws(mockRequest)).thenReturn(mockUnderlyingRequest)
      when(mockFactory.create[UnderlyingPutRecordRequest, UnderlyingPutRecordResult]()).thenReturn(handler)
      when(mockUnderlying.putRecordAsync(mockUnderlyingRequest, handler)).thenAnswer(invokeHandler)
      when(mockConverter.fromAws(mockUnderlyingResponse)).thenReturn(mockResponse)
      // Act.
      client.putRecord(mockRequest)
      // Assert.
      verify(mockConverter).toAws(mockRequest)
      verify(mockUnderlying).putRecordAsync(mockUnderlyingRequest, handler)
    }
  }

  s"$classUnderTest::putRecords" should {
    "invoke putRecordsAsync" in new PutRecordsContext {
      // Arrange.
      when(mockConverter.toAws(mockRequest)).thenReturn(mockUnderlyingRequest)
      when(mockFactory.create[UnderlyingPutRecordsRequest, UnderlyingPutRecordsResult]()).thenReturn(handler)
      when(mockUnderlying.putRecordsAsync(mockUnderlyingRequest, handler)).thenAnswer(invokeHandler)
      when(mockConverter.fromAws(mockUnderlyingResponse)).thenReturn(mockResponse)
      // Act.
      client.putRecords(mockRequest)
      // Assert.
      verify(mockConverter).toAws(mockRequest)
      verify(mockUnderlying).putRecordsAsync(mockUnderlyingRequest, handler)
    }
  }

  s"$classUnderTest::splitShard" should {
    "invoke splitShardAsync" in new SplitShardContext {
      // Arrange.
      when(mockConverter.toAws(mockRequest)).thenReturn(mockUnderlyingRequest)
      when(mockFactory.create[UnderlyingSplitShardRequest, Void]()).thenReturn(handler)
      when(mockUnderlying.splitShardAsync(mockUnderlyingRequest, handler)).thenAnswer(invokeHandler)
      // Act.
      client.splitShard(mockRequest)
      // Assert.
      verify(mockConverter).toAws(mockRequest)
      verify(mockUnderlying).splitShardAsync(mockUnderlyingRequest, handler)
    }
  }

  trait Context {
    val mockUnderlying = mock[Underlying]
    val mockConverter = mock[KinesisConverter]
    val mockFactory = mock[PromiseHandlerFactory]
    val client = new AmazonKinesisAsyncClientWrapper(mockUnderlying, mockConverter, mockFactory)
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

  class VoidTypedContext[
      Request <: AnyRef : Manifest,
      UnderlyingRequest <: AmazonWebServiceRequest : Manifest] extends PartiallyTypedContext[
      Request,
      UnderlyingRequest,
      Void] {
    def invokeHandler = new Answer[JFuture[Void]] {
      override def answer(invocation: InvocationOnMock): JFuture[Void] = {
        handler.onSuccess(mockUnderlyingRequest, null)
        mockJavaFuture
      }
    }
  }

  class CreateStreamContext extends VoidTypedContext[CreateStreamRequest, UnderlyingCreateStreamRequest]

  class DeleteStreamContext extends VoidTypedContext[DeleteStreamRequest, UnderlyingDeleteStreamRequest]

  class DescribeStreamContext extends FullyTypedContext[
    DescribeStreamRequest,
    DescribeStreamResponse,
    UnderlyingDescribeStreamRequest,
    UnderlyingDescribeStreamResult]

  class GetRecordsContext extends FullyTypedContext[
    GetRecordsRequest,
    GetRecordsResponse,
    UnderlyingGetRecordsRequest,
    UnderlyingGetRecordsResult]

  class GetShardIteratorContext extends FullyTypedContext[
    GetShardIteratorRequest,
    GetShardIteratorResponse,
    UnderlyingGetShardIteratorRequest,
    UnderlyingGetShardIteratorResult]

  class ListStreamsContext extends FullyTypedContext[
    ListStreamsRequest,
    ListStreamsResponse,
    UnderlyingListStreamsRequest,
    UnderlyingListStreamsResult]

  class MergeShardsContext extends VoidTypedContext[MergeShardsRequest, UnderlyingMergeShardsRequest]

  class PutRecordContext extends FullyTypedContext[
    PutRecordRequest,
    PutRecordResponse,
    UnderlyingPutRecordRequest,
    UnderlyingPutRecordResult]

  class PutRecordsContext extends FullyTypedContext[
    PutRecordsRequest,
    PutRecordsResponse,
    UnderlyingPutRecordsRequest,
    UnderlyingPutRecordsResult]

  class SplitShardContext extends VoidTypedContext[SplitShardRequest, UnderlyingSplitShardRequest]
}
