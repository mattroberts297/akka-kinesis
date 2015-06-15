package org.typetastic.aws.kinesis

import com.amazonaws.services.kinesis.{AmazonKinesisAsync => Underlying}
import org.typetastic.aws.handlers.{PromiseHandlerFactory, PromiseHandler}
import org.typetastic.aws.kinesis.model._

import scala.concurrent.{ExecutionContext, Promise, Future}

class KinesisClient(
    val underlying: Underlying,
    val converter: Converter,
    val factory: PromiseHandlerFactory)(
    implicit ec: ExecutionContext) {
  import converter._
  import factory._

  def createStream(request: CreateStreamRequest): Future[Unit] = {
    val promise = Promise[Void]()
    underlying.createStreamAsync(convert(request), create(promise))
    promise.future.map(_ => Unit)
  }

  def deleteStream(request: DeleteStreamRequest): Future[Unit] = {
    val promise = Promise[Void]()
    underlying.deleteStreamAsync(convert(request), create(promise))
    promise.future.map(_ => Unit)
  }
}
