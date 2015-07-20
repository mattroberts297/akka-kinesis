package org.typedsolutions.aws.kinesis.model

import org.scalatest.Matchers
import org.scalatest.WordSpec

class StreamStatusSpec extends WordSpec with Matchers {
  import StreamStatus._

  val stringsAndValues = Map(
    "CREATING" -> Creating,
    "DELETING" -> Deleting,
    "ACTIVE" -> Active,
    "UPDATING" -> Updating)

  stringsAndValues.map { case (string, value) =>
    s"The StreamStatus::apply($string)" should {
      s"return $value" in {
        StreamStatus.apply(string) should be (value)
      }
    }
  }
}
