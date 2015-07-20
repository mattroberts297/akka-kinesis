package org.typedsolutions.aws.kinesis.model

case class ListStreamsRequest(
    exclusiveStartStreamName: Option[String] = None,
    limit: Option[Int] = None) extends Command
