package org.typedsolutions.aws.util

import java.util

import scala.concurrent.ExecutionContext
import java.util.concurrent.{ AbstractExecutorService, TimeUnit }
import java.util.Collections

class ExecutionContextWrapper(context: ExecutionContext) extends AbstractExecutorService {
  override def shutdown(): Unit = {}

  override def isTerminated: Boolean = false

  override def awaitTermination(timeout: Long, unit: TimeUnit): Boolean = false

  override def shutdownNow(): util.List[Runnable] = Collections.emptyList()

  override def isShutdown: Boolean = false

  override def execute(runnable: Runnable): Unit = context.execute(runnable)
}
