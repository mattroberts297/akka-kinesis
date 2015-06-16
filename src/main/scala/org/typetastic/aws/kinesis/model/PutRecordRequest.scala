package org.typetastic.aws.kinesis.model

import akka.util.ByteString

case class PutRecordRequest(
    streamName: String,
    partitionKey: String,
    data: ByteString,
    sequenceNumberForOrdering: Option[String],
    explicitHashKey: Option[String])
