package org.typedsolutions.aws.kinesis.model

case class CommandFailed(command: Command, exception: Exception) extends Event
