package org.typetastic.aws.handlers

import com.amazonaws.AmazonWebServiceRequest

class PromiseHandlerFactory {
  def create[Request <: AmazonWebServiceRequest, Response](): PromiseHandler[Request, Response] = {
    new PromiseHandler()
  }
}
