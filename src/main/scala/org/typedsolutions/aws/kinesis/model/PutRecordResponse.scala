package org.typedsolutions.aws.kinesis.model

case class PutRecordResponse(shardId: String, sequenceNumber: String) extends Event
