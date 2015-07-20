package org.typedsolutions.aws.util

import java.util
import java.util.Collections
import java.util.concurrent.AbstractExecutorService
import java.util.concurrent.TimeUnit

import scala.concurrent.ExecutionContext

class ExecutionContextWrapper(context: ExecutionContext) extends AbstractExecutorService {
  override def awaitTermination(timeout: Long, unit: TimeUnit): Boolean = false

  override def execute(runnable: Runnable): Unit = context.execute(runnable)

  override def isShutdown: Boolean = false

  override def isTerminated: Boolean = false

  override def shutdown(): Unit = {}

  override def shutdownNow(): util.List[Runnable] = Collections.emptyList()
}
