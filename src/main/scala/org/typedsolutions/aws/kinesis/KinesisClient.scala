package org.typedsolutions.aws.kinesis

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Terminated
import akka.event.LoggingReceive
import org.typedsolutions.aws.kinesis.model._
import akka.pattern.pipe

import scala.concurrent.Future

class KinesisClient(owner: ActorRef, wrapper: AmazonKinesisAsyncWrapper) extends Actor {
  import context._

  override def preStart(): Unit = {
    super.preStart()
    owner ! KinesisClientCreated
    context.watch(owner)
  }

  override def receive: Receive = LoggingReceive {
    case command: CreateStreamRequest => handle(command)(sender())(wrapper.createStream)
    case command: DeleteStreamRequest => handle(command)(sender())(wrapper.deleteStream)
    case command: DescribeStreamRequest => handle(command)(sender())(wrapper.describeStream)
    case command: GetRecordsRequest => handle(command)(sender())(wrapper.getRecords)
    case command: GetShardIteratorRequest => handle(command)(sender())(wrapper.getShardIterator)
    case command: ListStreamsRequest => handle(command)(sender())(wrapper.listStreams)
    case command: MergeShardsRequest => handle(command)(sender())(wrapper.mergeShards)
    case command: PutRecordRequest => handle(command)(sender())(wrapper.putRecord)
    case command: PutRecordsRequest => handle(command)(sender())(wrapper.putRecords)
    case command: SplitShardRequest => handle(command)(sender())(wrapper.splitShard)
    case Terminated(`owner`) => wrapper.underlying.shutdown()
  }

  private def handle[C <: Command, E <: Event](command: C)(commander: ActorRef)(thunk: C => Future[E]): Unit =  {
    thunk(command).recover(withCommandFailed(command)).pipeTo(commander)
  }

  private def withCommandFailed(command: Command): PartialFunction[Throwable, CommandFailed] = {
    case exception: Exception => CommandFailed(command, exception)
  }
}
