package org.typetastic.aws.kinesis.model

case class DescribeStreamRequest(
    streamName: String,
    exclusiveStartShardId: Option[String],
    limit: Option[Int])
