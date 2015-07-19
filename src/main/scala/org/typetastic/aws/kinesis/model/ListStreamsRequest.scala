package org.typetastic.aws.kinesis.model

case class ListStreamsRequest(
    exclusiveStartStreamName: Option[String],
    limit: Option[Int]) extends Command
