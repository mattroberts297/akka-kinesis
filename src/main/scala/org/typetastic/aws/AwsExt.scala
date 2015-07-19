package org.typetastic.aws

import akka.actor.ActorRef
import akka.actor.Deploy
import akka.actor.ExtendedActorSystem
import akka.actor.Props
import akka.io.IO
import org.typetastic.aws.util.ActorNaming._

class AwsExt(system: ExtendedActorSystem) extends IO.Extension {
  override val manager: ActorRef = system.systemActorOf(
    props = Props(classOf[AwsManager]).withDeploy(Deploy.local), name = name[AwsManager])
}
