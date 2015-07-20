package org.typedsolutions.aws.handlers

import com.amazonaws.AmazonWebServiceRequest
import org.scalatest.Matchers
import org.scalatest.WordSpec

class PromiseHandlerFactorySpec extends WordSpec with Matchers {
  val classUnderTest = classOf[PromiseHandlerFactory].getSimpleName

  type Response = String

  s"The $classUnderTest::apply method" should {
    "return a PromiseHandler" in {
      val promiseHandler = PromiseHandlerFactory().create[AmazonWebServiceRequest, Response]()
      promiseHandler.getClass should be (classOf[PromiseHandler[AmazonWebServiceRequest, Response]])
    }
  }
}
