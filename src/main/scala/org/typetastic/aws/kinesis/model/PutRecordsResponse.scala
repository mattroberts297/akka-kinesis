package org.typetastic.aws.kinesis.model

case class PutRecordsResponse(
    failedRecordCount: Int,
    records: List[PutRecordsResponseEntry])

trait PutRecordsResponseEntry

case class PutRecordsResponseSuccessEntry(
    shardId: String,
    sequenceNumber: String) extends PutRecordsResponseEntry

case class PutRecordsResponseFailureEntry(
  errorCode: String,
  errorMessage: String) extends PutRecordsResponseEntry
