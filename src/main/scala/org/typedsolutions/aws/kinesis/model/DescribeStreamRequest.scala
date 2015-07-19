package org.typedsolutions.aws.kinesis.model

case class DescribeStreamRequest(
    streamName: String,
    exclusiveStartShardId: Option[String],
    limit: Option[Int]) extends Command
