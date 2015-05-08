package org.scalat.akka.aws

import com.amazonaws.services.kinesis.{AmazonKinesisAsyncClient, AmazonKinesisAsync}

class AwsSdkFactory(region: Aws.Region.Value) extends AwsFactory {
  def kinesis(): AmazonKinesisAsync = {
    new AmazonKinesisAsyncClient().withRegion(Aws.Region.underlying(region))
  }
}

object AwsSdkFactory {
  def apply(region: Aws.Region.Value): AwsFactory = new AwsSdkFactory(region)
}
