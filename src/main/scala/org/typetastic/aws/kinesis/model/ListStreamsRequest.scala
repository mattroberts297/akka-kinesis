package org.typetastic.aws.kinesis.model

case class ListStreamsRequest(
    exclusiveStartStreamName: String,
    limit: Option[Int])
