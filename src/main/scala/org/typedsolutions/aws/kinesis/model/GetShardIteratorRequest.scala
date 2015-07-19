package org.typedsolutions.aws.kinesis.model

case class GetShardIteratorRequest(
    streamName: String,
    shardId: String,
    shardIteratorType: ShardIteratorType.Value,
    startingSequenceNumber: Option[String] = None) extends Command

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

  def apply(string: String): Value = string match {
    case "AT_SEQUENCE_NUMBER" => AT_SEQUENCE_NUMBER
    case "AFTER_SEQUENCE_NUMBER" => AFTER_SEQUENCE_NUMBER
    case "TRIM_HORIZON" => TRIM_HORIZON
    case "LATEST" => LATEST
  }
}
