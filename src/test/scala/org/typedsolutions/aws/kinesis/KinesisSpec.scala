package org.typedsolutions.aws.kinesis

import akka.actor.ActorRef
import akka.io.IO
import org.typedsolutions.aws.util.AkkaSpec

class KinesisSpec extends AkkaSpec {
  val classUnderTest = Kinesis.getClass.getSimpleName.dropRight(1)

  s"The $classUnderTest extension" should {
    "return an ActorRef" in {
      val manager = IO(Kinesis)
      manager should not be (null)
      classOf[ActorRef].isAssignableFrom(manager.getClass) should be (true)
    }
  }
}
