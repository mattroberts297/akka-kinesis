package org.typedsolutions.aws.kinesis

import akka.actor.ExtendedActorSystem
import akka.actor.Extension
import akka.actor.ExtensionId
import akka.actor.ExtensionIdProvider

object Kinesis extends ExtensionId[KinesisExt] with ExtensionIdProvider {
  override def createExtension(system: ExtendedActorSystem): KinesisExt = {
    new KinesisExt(system)
  }

  override def lookup(): ExtensionId[_ <: Extension] = {
    Kinesis
  }
}
