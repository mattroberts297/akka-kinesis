package org.scalat.akka.aws

import com.amazonaws.services.kinesis.AmazonKinesisAsync

trait AwsFactory {
  def kinesis(): AmazonKinesisAsync
}
