package org.typedsolutions.aws.kinesis.model

import akka.util.ByteString

case class PutRecordsRequest(
    streamName: String,
    records: List[PutRecordsRequestEntry]) extends Command


case class PutRecordsRequestEntry(
    partitionKey: String,
    data: ByteString,
    explicitHashKey: Option[String])
