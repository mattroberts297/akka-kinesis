package org.typetastic.aws.kinesis.model

import akka.util.ByteString

case class PutRecordsRequest(
    streamName: String,
    records: List[PutRecordsRequestEntry])


case class PutRecordsRequestEntry(
    partitionKey: String,
    data: ByteString,
    explicitHashKey: Option[String])
