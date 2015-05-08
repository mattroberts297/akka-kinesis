package org.scalat.akka.aws

import akka.actor.{Deploy, Props, ActorRef, ExtendedActorSystem}
import akka.io.IO

class AwsExt(system: ExtendedActorSystem, factory: AwsFactory) extends IO.Extension {
  override val manager: ActorRef = system.systemActorOf(
    props = Props(classOf[AwsManager], factory).withDeploy(Deploy.local), name = "IO-AWS")
}
