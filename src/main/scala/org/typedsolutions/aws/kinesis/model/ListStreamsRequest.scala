package org.typedsolutions.aws.kinesis.model

case class ListStreamsRequest(
    exclusiveStartStreamName: Option[String],
    limit: Option[Int]) extends Command
