package org.typetastic.aws.handlers

import com.amazonaws.AmazonWebServiceRequest

import scala.concurrent.Promise

class PromiseHandlerFactory {
  def create[Request <: AmazonWebServiceRequest, Response](
    promise: Promise[Response]): PromiseHandler[Request, Response] = {
    new PromiseHandler(promise)
  }
}
