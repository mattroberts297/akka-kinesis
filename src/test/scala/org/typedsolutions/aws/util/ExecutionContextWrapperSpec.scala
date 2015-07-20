package org.typedsolutions.aws.util

import java.util.Collections
import java.util.concurrent.TimeUnit

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{WordSpec, Matchers}

import scala.concurrent.ExecutionContext

class ExecutionContextWrapperSpec extends WordSpec with Matchers with MockitoSugar {
  val classUnderTest = classOf[ExecutionContextWrapper].getSimpleName
  val underlyingClass = classOf[ExecutionContext].getSimpleName

  s"The $classUnderTest" should {
    s"not interact with the $underlyingClass" when {
      "awaitTermination is invoked" in new Context {
        executionContextWrapper.awaitTermination(aTime, aTimeUnit)
        verifyZeroInteractions(mockExecutionContext)
      }

      "isShutdown is invoked" in new Context {
        executionContextWrapper.isShutdown
        verifyZeroInteractions(mockExecutionContext)
      }

      "isTerminated is invoked" in new Context {
        executionContextWrapper.isTerminated
        verifyZeroInteractions(mockExecutionContext)
      }

      "shutdown is invoked" in new Context {
        executionContextWrapper.shutdown()
        verifyZeroInteractions(mockExecutionContext)
      }

      "shutdownNow is invoked" in new Context {
        executionContextWrapper.shutdownNow()
        verifyZeroInteractions(mockExecutionContext)
      }
    }

    s"invoke execute on the $underlyingClass" when {
      "execute is invoked" in new Context {
        executionContextWrapper.execute(mockRunnable)
        verify(mockExecutionContext).execute(mockRunnable)
      }
    }

    "return false" when {
      "awaitTermination is invoked" in new Context {
        executionContextWrapper.awaitTermination(aTime, aTimeUnit) should be (false)
      }

      "isShutdown is invoked" in new Context {
        executionContextWrapper.isShutdown should be (false)
      }

      "isTerminated is invoked" in new Context {
        executionContextWrapper.isTerminated should be (false)
      }
    }

    "return an empty list" when {
      "shutdownNow is invoked" in new Context {
        executionContextWrapper.shutdownNow() should be (Collections.emptyList())
      }
    }
  }

  trait Context {
    val mockExecutionContext = mock[ExecutionContext]
    val mockRunnable = mock[Runnable]
    val aTime = 100
    val aTimeUnit = TimeUnit.MILLISECONDS
    val executionContextWrapper = new ExecutionContextWrapper(mockExecutionContext)
  }
}
