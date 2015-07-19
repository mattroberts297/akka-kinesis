package org.typedsolutions.aws.kinesis.model

case class PutRecordsResponse(
    failedRecordCount: Int,
    records: List[PutRecordsResponseEntry]) extends Event

sealed trait PutRecordsResponseEntry

case class PutRecordsResponseSuccessEntry(
    shardId: String,
    sequenceNumber: String) extends PutRecordsResponseEntry

case class PutRecordsResponseFailureEntry(
  errorCode: String,
  errorMessage: String) extends PutRecordsResponseEntry
