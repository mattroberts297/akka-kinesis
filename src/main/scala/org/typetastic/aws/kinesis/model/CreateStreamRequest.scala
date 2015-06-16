package org.typetastic.aws.kinesis.model

case class CreateStreamRequest(streamName: String, shardCount: Int)
