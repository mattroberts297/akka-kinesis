package org.scalat.akka.aws

import akka.actor._
import akka.util.ByteString
import com.amazonaws.regions.{Regions, Region => UnderlyingRegion}

/**
 * AWS extension for Akka's IO layer.
 */
class Aws(private val region: Aws.Region.Value)
  extends ExtensionId[AwsExt]
  with ExtensionIdProvider {

  override def createExtension(system: ExtendedActorSystem): AwsExt = {
    new AwsExt(system, new AwsSdkFactory(region))
  }

  override def lookup(): ExtensionId[_ <: Extension] = {
    Aws(region)
  }
}

object Aws {
  sealed trait Message

  // Commands

  trait Command extends Message

  case object Kinesis extends Command

  case class ListStreams(
      exclusiveStartStreamName: Option[String] = None,
      limit: Option[Int] = None) extends Command

  case class DescribeStream(
      streamName: String,
      exclusiveStartShardId: Option[String] = None,
      limit: Option[Int] = None) extends Command

  case class GetShardIterator(
      streamName: String,
      shardId: String,
      shardIteratorType: ShardIteratorType.Value,
      startingSequenceNumber: Option[String] = None) extends Command

  case class GetRecords(
      shardIterator: String,
      limit: Option[Int]) extends Command

  // Results

  trait Result extends Message

  case class CommandFailed(cmd: Command, throwable: Throwable) extends Result

  case object KinesisResult extends Result

  case class ListStreamsResult(
      streamNames: List[String],
      hasMoreStreams: Boolean) extends Result

  case class DescribeStreamResult(
      name: String,
      arn: String,
      status: String,
      shards: List[Shard]) extends Result

  case class GetShardIteratorResult(shardIterator: String) extends Result

  case class GetRecordsResult(nextShardIterator: String, records: List[Record])

  // Models

  case class Shard(
      id: String,
      adjacentParentId: String,
      parentId: String,
      hashKeyRange: HashKeyRange,
      sequenceNumberRange: SequenceNumberRange)

  case class HashKeyRange(firstHashKey: String, lastHashKey: String)

  case class SequenceNumberRange(
      firstSequenceNumber: String,
      lastSequenceNumber: String)

  case class Record(
      partitionKey: String,
      sequenceNumber: String,
      data: ByteString)

  // Enumerations

  object Region {
    sealed trait Value
    case object EU_WEST_1 extends Value
    case object US_EAST_1 extends Value

    def underlying(value: Value): UnderlyingRegion = value match {
      case EU_WEST_1 => UnderlyingRegion.getRegion(Regions.EU_WEST_1)
      case US_EAST_1 => UnderlyingRegion.getRegion(Regions.US_EAST_1)
    }
  }

  object ShardIteratorType {
    sealed trait Value
    case object AT_SEQUENCE_NUMBER extends Value
    case object AFTER_SEQUENCE_NUMBER extends Value
    case object TRIM_HORIZON extends Value
    case object LATEST extends Value

    def underlying(value: Value): String = value match {
      case AT_SEQUENCE_NUMBER => "AT_SEQUENCE_NUMBER"
      case AFTER_SEQUENCE_NUMBER => "AFTER_SEQUENCE_NUMBER"
      case TRIM_HORIZON => "TRIM_HORIZON"
      case LATEST => "LATEST"
    }
  }

  def apply(region: Region.Value): Aws = new Aws(region)
}