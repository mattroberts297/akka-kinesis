package org.typedsolutions.aws.handlers

import com.amazonaws.AmazonWebServiceRequest

class PromiseHandlerFactory {
  def create[Request <: AmazonWebServiceRequest, Response](): PromiseHandler[Request, Response] = {
    new PromiseHandler()
  }
}

object PromiseHandlerFactory {
  def apply(): PromiseHandlerFactory = {
    new PromiseHandlerFactory
  }
}
