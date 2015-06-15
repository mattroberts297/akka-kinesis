package org.typetastic.akka.kinesis

import com.amazonaws.services.kinesis.{AmazonKinesisAsync => Underlying}

class KinesisClient(val underlying: Underlying) {

}
