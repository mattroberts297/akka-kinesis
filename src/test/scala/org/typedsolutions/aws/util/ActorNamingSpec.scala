package org.typedsolutions.aws.util

import java.io.BufferedInputStream

import org.scalatest.mock.MockitoSugar
import org.scalatest.{WordSpec, Matchers}

class ActorNamingSpec extends WordSpec with Matchers {
  val classUnderTest = classOf[ActorNaming].getSimpleName

  s"The $classUnderTest name method" should {
    "return buffered-input-stream" when {
      "invoked with BufferedInputStream" in new Context {
        name[BufferedInputStream] should be ("buffered-input-stream")
      }
    }

    "return string" when {
      "invoked with String" in new Context {
        name[String] should be ("string")
      }
    }
  }

  s"The $classUnderTest uniqueName method" should {
    "return a string starting with buffered-input-stream" when {
      "invoked with BufferedInputStream" in new Context {
        uniqueName[BufferedInputStream] should startWith ("buffered-input-stream-")
      }
    }

    "return two unique strings" when {
      "invoked multiple times" in new Context {
        uniqueName[BufferedInputStream] should not be uniqueName[BufferedInputStream]
      }
    }
  }

  trait Context extends ActorNaming
}
