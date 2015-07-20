package org.typedsolutions.aws.kinesis

import akka.actor.ActorRef
import akka.actor.Deploy
import akka.actor.ExtendedActorSystem
import akka.actor.Props
import akka.io.IO
import org.typedsolutions.aws.util.ActorNaming._

class KinesisExt(system: ExtendedActorSystem) extends IO.Extension {
  override val manager: ActorRef = system.systemActorOf(
    props = Props(classOf[KinesisManager]).withDeploy(Deploy.local),
    name = name[KinesisManager])
}
