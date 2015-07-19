package org.typedsolutions.aws.kinesis.model

import akka.util.ByteString

case class PutRecordRequest(
    streamName: String,
    partitionKey: String,
    data: ByteString,
    explicitHashKey: Option[String],
    sequenceNumberForOrdering: Option[String]) extends Command
