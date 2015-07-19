package org.typedsolutions.aws

import akka.actor.ExtendedActorSystem
import akka.actor.Extension
import akka.actor.ExtensionId
import akka.actor.ExtensionIdProvider

object Aws extends ExtensionId[AwsExt] with ExtensionIdProvider {
  override def createExtension(system: ExtendedActorSystem): AwsExt = {
    new AwsExt(system)
  }

  override def lookup(): ExtensionId[_ <: Extension] = {
    Aws
  }
}
