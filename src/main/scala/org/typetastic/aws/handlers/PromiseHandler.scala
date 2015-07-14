package org.typetastic.aws.handlers

import com.amazonaws.AmazonWebServiceRequest
import com.amazonaws.handlers.AsyncHandler

import scala.concurrent.Promise

class PromiseHandler[Request <: AmazonWebServiceRequest, Response](
    private val promise: Promise[Response] = Promise[Response]())
  extends AsyncHandler[Request, Response] {

  def future = promise.future

  override def onError(exception: Exception): Unit = {
    promise.failure(exception)
  }

  override def onSuccess(request: Request, result: Response): Unit = {
    promise.success(result)
  }
}

object PromiseHandler {
  def apply[Request <: AmazonWebServiceRequest, Response](
      promise: Promise[Response] = Promise[Response]()): PromiseHandler[Request, Response] = {
    new PromiseHandler(promise)
  }
}
