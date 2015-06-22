package org.typetastic.aws.kinesis.model

case class DescribeStreamResponse(
    streamName: String,
    streamArn: String,
    streamStatus: StreamStatus.Value,
    shards: List[Shard],
    hasMoreShards: Boolean)

case class Shard(
    id: String,
    parentId: String,
    adjacentParentId: String,
    hashKeyRange: HashKeyRange,
    sequenceNumberRange: SequenceNumberRange)

case class HashKeyRange(startingHashKey: String, endingHashKey: String)

case class SequenceNumberRange(
    startingSequenceNumber: String,
    endingSequenceNumber: String)

object StreamStatus {
  sealed trait Value
  case object Active extends Value
  case object Creating extends Value
  case object Deleting extends Value
  case object Updating extends Value

  def apply(s: String): Value = s match {
    case "ACTIVE" => Active
    case "CREATING" => Creating
    case "DELETING" => Deleting
    case "UPDATING" => Updating
  }
}
