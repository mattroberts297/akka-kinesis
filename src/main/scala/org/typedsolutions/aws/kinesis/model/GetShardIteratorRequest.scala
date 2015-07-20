package org.typedsolutions.aws.kinesis.model

case class GetShardIteratorRequest(
    streamName: String,
    shardId: String,
    shardIteratorType: ShardIteratorType.Value,
    startingSequenceNumber: Option[String] = None) extends Command

object ShardIteratorType {
  sealed trait Value
  case object AtSequenceNumber extends Value
  case object AfterSequenceNumber extends Value
  case object TrimHorizon extends Value
  case object Latest extends Value

  def underlying(value: Value): String = value match {
    case AtSequenceNumber => "AT_SEQUENCE_NUMBER"
    case AfterSequenceNumber => "AFTER_SEQUENCE_NUMBER"
    case TrimHorizon => "TRIM_HORIZON"
    case Latest => "LATEST"
  }

  def apply(string: String): Value = string match {
    case "AT_SEQUENCE_NUMBER" => AtSequenceNumber
    case "AFTER_SEQUENCE_NUMBER" => AfterSequenceNumber
    case "TRIM_HORIZON" => TrimHorizon
    case "LATEST" => Latest
  }
}
