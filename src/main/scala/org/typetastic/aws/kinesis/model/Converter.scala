package org.typetastic.aws.kinesis.model

import com.amazonaws.services.kinesis.model.{CreateStreamRequest => UnderlyingCreateStreamRequest, DeleteStreamRequest => UnderlyingDeleteStreamRequest}

class Converter {
  def convert(createStreamRequest: CreateStreamRequest): UnderlyingCreateStreamRequest = {
    new UnderlyingCreateStreamRequest().
      withShardCount(createStreamRequest.shardCount).
      withStreamName(createStreamRequest.streamName)
  }

  def convert(deleteStreamRequest: DeleteStreamRequest): UnderlyingDeleteStreamRequest = {
    new UnderlyingDeleteStreamRequest().
      withStreamName(deleteStreamRequest.streamName)
  }
}
