package org.typetastic.aws.kinesis.model

case class CommandFailed(command: Command, exception: Exception) extends Event
