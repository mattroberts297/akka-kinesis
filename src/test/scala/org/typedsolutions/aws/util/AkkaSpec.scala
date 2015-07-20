package org.typedsolutions.aws.util

import akka.actor.ActorSystem
import akka.testkit.ImplicitSender
import akka.testkit.TestKit
import org.scalatest.BeforeAndAfterAll
import org.scalatest.Matchers
import org.scalatest.WordSpecLike
import org.typedsolutions.aws.util.ActorNaming._
import scala.concurrent.duration._

abstract class AkkaSpec
  extends TestKit(ActorSystem(uniqueName[AkkaSpec]))
  with ImplicitSender
  with WordSpecLike
  with Matchers
  with BeforeAndAfterAll {

  implicit val timeout = 100.milliseconds

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }
}

object AkkaSpec {
  def TestSystem = ActorSystem("test-system")
}
