package org.typedsolutions.aws.kinesis

import akka.actor.PoisonPill
import akka.actor.Props
import akka.testkit.TestActorRef
import akka.testkit.TestProbe
import org.scalatest.mock.MockitoSugar
import org.typedsolutions.aws.kinesis.model._
import org.typedsolutions.aws.util.AkkaSpec
import org.mockito.Mockito.{timeout => timeoutInMillis, _}
import com.amazonaws.services.kinesis.{AmazonKinesisAsync => Underlying}

import scala.concurrent.Future

class AmazonKinesisActorSpec extends AkkaSpec {
  val classUnderTest = classOf[AmazonKinesisActor].getSimpleName

  s"The $classUnderTest" when {
    s"started" should {
      s"send its owner a $KinesisClientCreated" in new Context {
        owner.expectMsg(timeout, KinesisClientCreated)
      }
    }

    s"The $classUnderTest" when {
      s"sent Terminated(owner)" should {
        s"call shutdown on the wrapper" in new Context {
          when(mockWrapper.underlying).thenReturn(mockUnderlying)
          owner.ref ! PoisonPill
          verify(mockUnderlying, timeoutInMillis(timeout.toMillis)).shutdown()
        }
      }
    }

    s"sent a $CreateStreamRequest" should {
      s"reply with a $CreateStreamResponse on success" in {
        new SuccessfulContext[CreateStreamRequest, CreateStreamResponse] {
          override def methodToInvoke = mockWrapper.createStream _
          client ! mockRequest
          expectMsg(timeout, mockResponse)
        }
      }

      s"reply with a $CommandFailed on failure" in {
        new FailureContext[CreateStreamRequest, CreateStreamResponse]  {
          override def methodToInvoke = mockWrapper.createStream _
          client ! mockRequest
          expectMsg(timeout, CommandFailed(mockRequest, mockException))
        }
      }
    }

    s"sent a $DeleteStreamRequest" should {
      s"reply with a $DeleteStreamResponse on success" in {
        new SuccessfulContext[DeleteStreamRequest, DeleteStreamResponse] {
          override def methodToInvoke = mockWrapper.deleteStream _
          client ! mockRequest
          expectMsg(timeout, mockResponse)
        }
      }

      s"reply with a $CommandFailed on failure" in {
        new FailureContext[DeleteStreamRequest, DeleteStreamResponse]  {
          override def methodToInvoke = mockWrapper.deleteStream _
          client ! mockRequest
          expectMsg(timeout, CommandFailed(mockRequest, mockException))
        }
      }
    }

    s"sent a $DescribeStreamRequest" should {
      s"reply with a $DescribeStreamResponse on success" in {
        new SuccessfulContext[DescribeStreamRequest, DescribeStreamResponse] {
          override def methodToInvoke = mockWrapper.describeStream _
          client ! mockRequest
          expectMsg(timeout, mockResponse)
        }
      }

      s"reply with a $CommandFailed on failure" in {
        new FailureContext[DescribeStreamRequest, DescribeStreamResponse]  {
          override def methodToInvoke = mockWrapper.describeStream _
          client ! mockRequest
          expectMsg(timeout, CommandFailed(mockRequest, mockException))
        }
      }
    }

    s"sent a $GetRecordsRequest" should {
      s"reply with a $GetRecordsResponse on success" in {
        new SuccessfulContext[GetRecordsRequest, GetRecordsResponse] {
          override def methodToInvoke = mockWrapper.getRecords _
          client ! mockRequest
          expectMsg(timeout, mockResponse)
        }
      }

      s"reply with a $CommandFailed on failure" in {
        new FailureContext[GetRecordsRequest, GetRecordsResponse]  {
          override def methodToInvoke = mockWrapper.getRecords _
          client ! mockRequest
          expectMsg(timeout, CommandFailed(mockRequest, mockException))
        }
      }
    }

    s"sent a $GetShardIteratorRequest" should {
      s"reply with a $GetShardIteratorResponse on success" in {
        new SuccessfulContext[GetShardIteratorRequest, GetShardIteratorResponse] {
          override def methodToInvoke = mockWrapper.getShardIterator _
          client ! mockRequest
          expectMsg(timeout, mockResponse)
        }
      }

      s"reply with a $CommandFailed on failure" in {
        new FailureContext[GetShardIteratorRequest, GetShardIteratorResponse]  {
          override def methodToInvoke = mockWrapper.getShardIterator _
          client ! mockRequest
          expectMsg(timeout, CommandFailed(mockRequest, mockException))
        }
      }
    }

    s"sent a $ListStreamsRequest" should {
      s"reply with a $ListStreamsResponse on success" in {
        new SuccessfulContext[ListStreamsRequest, ListStreamsResponse] {
          override def methodToInvoke = mockWrapper.listStreams _
          client ! mockRequest
          expectMsg(timeout, mockResponse)
        }
      }

      s"reply with a $CommandFailed on failure" in {
        new FailureContext[ListStreamsRequest, ListStreamsResponse]  {
          override def methodToInvoke = mockWrapper.listStreams _
          client ! mockRequest
          expectMsg(timeout, CommandFailed(mockRequest, mockException))
        }
      }
    }

    s"sent a $MergeShardsRequest" should {
      s"reply with a $MergeShardsResponse on success" in {
        new SuccessfulContext[MergeShardsRequest, MergeShardsResponse] {
          override def methodToInvoke = mockWrapper.mergeShards _
          client ! mockRequest
          expectMsg(timeout, mockResponse)
        }
      }

      s"reply with a $CommandFailed on failure" in {
        new FailureContext[MergeShardsRequest, MergeShardsResponse]  {
          override def methodToInvoke = mockWrapper.mergeShards _
          client ! mockRequest
          expectMsg(timeout, CommandFailed(mockRequest, mockException))
        }
      }
    }

    s"sent a $PutRecordRequest" should {
      s"reply with a $PutRecordResponse on success" in {
        new SuccessfulContext[PutRecordRequest, PutRecordResponse] {
          override def methodToInvoke = mockWrapper.putRecord _
          client ! mockRequest
          expectMsg(timeout, mockResponse)
        }
      }

      s"reply with a $CommandFailed on failure" in {
        new FailureContext[PutRecordRequest, PutRecordResponse]  {
          override def methodToInvoke = mockWrapper.putRecord _
          client ! mockRequest
          expectMsg(timeout, CommandFailed(mockRequest, mockException))
        }
      }
    }

    s"sent a $PutRecordsRequest" should {
      s"reply with a $PutRecordsResponse on success" in {
        new SuccessfulContext[PutRecordsRequest, PutRecordsResponse] {
          override def methodToInvoke = mockWrapper.putRecords _
          client ! mockRequest
          expectMsg(timeout, mockResponse)
        }
      }

      s"reply with a $CommandFailed on failure" in {
        new FailureContext[PutRecordsRequest, PutRecordsResponse]  {
          override def methodToInvoke = mockWrapper.putRecords _
          client ! mockRequest
          expectMsg(timeout, CommandFailed(mockRequest, mockException))
        }
      }
    }

    s"sent a $SplitShardRequest" should {
      s"reply with a $SplitShardResponse on success" in {
        new SuccessfulContext[SplitShardRequest, SplitShardResponse] {
          override def methodToInvoke = mockWrapper.splitShard _
          client ! mockRequest
          expectMsg(timeout, mockResponse)
        }
      }

      s"reply with a $CommandFailed on failure" in {
        new FailureContext[SplitShardRequest, SplitShardResponse]  {
          override def methodToInvoke = mockWrapper.splitShard _
          client ! mockRequest
          expectMsg(timeout, CommandFailed(mockRequest, mockException))
        }
      }
    }
  }

  trait Context extends MockitoSugar {
    val owner = TestProbe()
    val mockWrapper = mock[AmazonKinesis]
    val mockUnderlying = mock[Underlying]
    val client = TestActorRef(Props(classOf[AmazonKinesisActor], owner.ref, mockWrapper))
  }

  abstract class GenericContext[Request <: AnyRef : Manifest, Response <: AnyRef : Manifest] extends Context {
    val mockRequest = mock[Request]
    val mockResponse = mock[Response]
  }

  trait SuccessfulContext[Request <: AnyRef, Response <: AnyRef] extends GenericContext[Request, Response] {
    def methodToInvoke: Request => Future[Response]
    when(methodToInvoke(mockRequest)).thenReturn(Future.successful(mockResponse))
  }

  trait FailureContext[Request <: AnyRef, Response <: AnyRef] extends GenericContext[Request, Response] {
    def methodToInvoke: Request => Future[Response]
    val mockException = mock[IllegalArgumentException]
    when(methodToInvoke(mockRequest)).thenReturn(Future.failed(mockException))
  }
}
