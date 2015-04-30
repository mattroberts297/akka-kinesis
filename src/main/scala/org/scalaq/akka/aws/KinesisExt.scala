package org.scalaq.akka.aws

import akka.actor.{Deploy, Props, ActorRef, ExtendedActorSystem}
import akka.io.IO

class KinesisExt(system: ExtendedActorSystem) extends IO.Extension {
  override val manager: ActorRef = system.systemActorOf(
    props = Props(classOf[KinesisManager], this).withDeploy(Deploy.local), name = "IO-KINESIS")
}
