package org.typetastic.aws.kinesis.model

import akka.util.ByteString

case class GetRecordsResponse(nextShardIterator: String, records: List[Record])

case class Record(
    partitionKey: String,
    sequenceNumber: String,
    data: ByteString)
