package org.typedsolutions.aws.handlers

import com.amazonaws.AmazonWebServiceRequest
import org.scalatest.Matchers
import org.scalatest.WordSpec
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._

import scala.concurrent.Promise

class PromiseHandlerSpec extends WordSpec with Matchers with MockitoSugar {
  type Response = Object

  val classUnderTest = classOf[PromiseHandler[AmazonWebServiceRequest, Response]].getSimpleName

  s"$classUnderTest::onError" should {
    "invoke failure on the Promise " in new Context {
      promiseHandler.onError(mockException)
      verify(mockPromise).failure(mockException)
    }
  }

  s"$classUnderTest::onSuccess" should {
    "invoke success on the Promise " in new Context {
      promiseHandler.onSuccess(mockRequest, mockResponse)
      verify(mockPromise).success(mockResponse)
    }
  }

  s"$classUnderTest::future" should {
    "invoke future on the Promise " in new Context {
      promiseHandler.future
      verify(mockPromise).future
    }
  }

  trait Context {
    val mockPromise = mock[Promise[Response]]
    val mockException = mock[IllegalArgumentException]
    val mockRequest = mock[AmazonWebServiceRequest]
    val mockResponse = mock[Response]
    val promiseHandler = PromiseHandler[AmazonWebServiceRequest, Response](mockPromise)
  }
}
