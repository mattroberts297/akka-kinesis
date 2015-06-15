package org.typetastic.aws.kinesis.model

case class CreateStreamRequest(shardCount: Int, streamName: String)
