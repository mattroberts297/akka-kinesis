package org.scalat.akka.aws

import akka.actor._

/**
 * AWS extension for Akka's IO layer.
 */
object Aws extends ExtensionId[AwsExt] with ExtensionIdProvider {
  sealed trait Message

  trait Command extends Message

  case object Kinesis extends Command

  case class ListStreams(exclusiveStartStreamName: Option[String] = None, limit: Option[Int] = None) extends Command

  case class DescribeStream(streamName: String) extends Command

  trait Result extends Message

  case class CommandFailed(cmd: Command, throwable: Throwable) extends Result

  case object KinesisResult extends Result

  case class ListStreamsResult(streamNames: List[String], hasMoreStreams: Boolean)

  case class DescribeStreamResult(streamDescription: String) extends Result

  override def createExtension(system: ExtendedActorSystem): AwsExt = new AwsExt(system)

  override def lookup(): ExtensionId[_ <: Extension] = Aws
}

