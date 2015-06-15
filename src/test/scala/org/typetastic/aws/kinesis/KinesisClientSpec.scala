package org.typetastic.aws.kinesis

import org.scalatest.mock.MockitoSugar
import org.scalatest.{WordSpec, Matchers}
import com.amazonaws.services.kinesis.{AmazonKinesisAsync => Underlying}
import org.typetastic.aws.handlers.{PromiseHandler, PromiseHandlerFactory}
import org.typetastic.aws.kinesis.model.{DeleteStreamRequest, Converter, CreateStreamRequest}
import org.mockito.Mockito._
import org.mockito.Matchers._
import com.amazonaws.services.kinesis.model.{CreateStreamRequest => UnderlyingCreateStreamRequest}
import com.amazonaws.services.kinesis.model.{DeleteStreamRequest => UnderlyingDeleteStreamRequest}

import scala.concurrent.Promise

class KinesisClientSpec extends WordSpec with Matchers with MockitoSugar {
  val classUnderTest = classOf[KinesisClient].getSimpleName

  trait Context {
    val underlying = mock[Underlying]
    val converter = mock[Converter]
    val factory = mock[PromiseHandlerFactory]

    import scala.concurrent.ExecutionContext.Implicits.global
    val client = new KinesisClient(underlying, converter, factory)
  }

  s"A $classUnderTest instance" when {
    "createStream is invoked" should {
      "invoke createStreamAsync on the underlying client" in new Context {
        // Arrange.
        val request = CreateStreamRequest(1, "Test")
        val underlyingRequest = mock[UnderlyingCreateStreamRequest]
        val handler = mock[PromiseHandler[UnderlyingCreateStreamRequest, Void]]
        when(converter.convert(request)).thenReturn(underlyingRequest)
        when(factory.create[UnderlyingCreateStreamRequest, Void](any[Promise[Void]])).thenReturn(handler)
        // Act.
        client.createStream(request)
        // Assert
        verify(converter).convert(request)
        verify(underlying).createStreamAsync(underlyingRequest, handler)
      }

      "invoke deleteStreamAsync on the underlying client" in new Context {
        // Arrange.
        val request = DeleteStreamRequest("Test")
        val underlyingRequest = mock[UnderlyingDeleteStreamRequest]
        val handler = mock[PromiseHandler[UnderlyingDeleteStreamRequest, Void]]
        when(converter.convert(request)).thenReturn(underlyingRequest)
        when(factory.create[UnderlyingDeleteStreamRequest, Void](any[Promise[Void]])).thenReturn(handler)
        // Act.
        client.deleteStream(request)
        // Assert
        verify(converter).convert(request)
        verify(underlying).deleteStreamAsync(underlyingRequest, handler)
      }
    }
  }
}
