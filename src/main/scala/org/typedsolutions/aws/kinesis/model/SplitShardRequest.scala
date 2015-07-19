package org.typedsolutions.aws.kinesis.model

case class SplitShardRequest(
    streamName: String,
    shardToSplit: String,
    newStartingHashKey: String) extends Command
