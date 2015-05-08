package org.scalat.akka.aws

import com.amazonaws.AmazonWebServiceRequest
import com.amazonaws.handlers.AsyncHandler

import scala.concurrent.Promise

class PromiseBasedAsyncHandler[Request <: AmazonWebServiceRequest, Response](
    promise: Promise[Response]) extends AsyncHandler[Request, Response] {
  override def onError(exception: Exception): Unit = promise.failure(exception)

  override def onSuccess(request: Request, result: Response): Unit = promise.success(result)
}

object PromiseBasedAsyncHandler {
  def apply[Request <: AmazonWebServiceRequest, Response](
      promise: Promise[Response]): PromiseBasedAsyncHandler[Request, Response] = {
    new PromiseBasedAsyncHandler(promise)
  }
}
