package org.typedsolutions.aws.kinesis.model

import akka.util.ByteString

case class GetRecordsResponse(nextShardIterator: String, records: List[Record]) extends Event

case class Record(
    partitionKey: String,
    sequenceNumber: String,
    data: ByteString)
