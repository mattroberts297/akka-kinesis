package org.typedsolutions.aws.kinesis.model

case class CreateStreamRequest(streamName: String, shardCount: Int) extends Command
