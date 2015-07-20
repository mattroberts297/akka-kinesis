package org.typedsolutions.aws.kinesis.model

import org.scalatest.Matchers
import org.scalatest.WordSpec

class ShardIteratorTypeSpec extends WordSpec with Matchers {
  import ShardIteratorType._

  val stringsAndValues = Map(
    "AT_SEQUENCE_NUMBER" -> AtSequenceNumber,
    "AFTER_SEQUENCE_NUMBER" -> AfterSequenceNumber,
    "TRIM_HORIZON" -> TrimHorizon,
    "LATEST" -> Latest)

  stringsAndValues.map { case (string, value) =>
    s"The ShardIteratorType::apply($string)" should {
      s"return $value" in {
        ShardIteratorType.apply(string) should be (value)
      }
    }
  }

  stringsAndValues.map { case (string, value) =>
    s"The ShardIteratorType::underling($value)" should {
      s"return $string" in {
        ShardIteratorType.underlying(value) should be (string)
      }
    }
  }
}
