package org.typedsolutions.aws.kinesis.model

case class MergeShardsRequest(
    streamName: String,
    shardToMerge: String,
    adjacentShardToMerge: String) extends Command
