package org.typetastic.aws.kinesis.model

case class ListStreamsResponse(
    hasMoreStreams: Boolean,
    streamNames: List[String])
