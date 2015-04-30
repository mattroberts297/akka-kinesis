package org.scalaq.akka.aws

import akka.actor._
import akka.io.IO

/**
 * Kinesis extension for Akka's IO layer.
 */
object Kinesis extends ExtensionId[KinesisExt] with ExtensionIdProvider {
  sealed trait Message

  trait Command extends Message

  case object CreateClient extends Command

  case class ListStreams(exclusiveStartStreamName: Option[String], limit: Option[Int]) extends Command

  case class DescribeStream(streamName: String) extends Command

  trait Result extends Message

  case class CommandFailed(cmd: Command, exception: Exception) extends Result

  case object ClientCreated extends Result

  case class ListStreamsResult(streamNames: List[String], hasMoreStreams: Boolean)

  case class DescribeStreamResult(streamDescription: String) extends Result

  override def createExtension(system: ExtendedActorSystem): KinesisExt = new KinesisExt(system)

  override def lookup(): ExtensionId[_ <: Extension] = Kinesis
}

