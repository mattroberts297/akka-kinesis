package org.typedsolutions.aws.kinesis.model

import org.scalatest.Matchers
import org.scalatest.WordSpec
import com.amazonaws.regions.{Regions => UnderlyingRegions, Region => UnderlyingRegion}
import org.typedsolutions.aws.kinesis.model.Region._

class RegionSpec extends WordSpec with Matchers {

  val valuesAndEnums = Map(
    GovCloud -> UnderlyingRegion.getRegion(UnderlyingRegions.GovCloud),
    UsEast1 -> UnderlyingRegion.getRegion(UnderlyingRegions.US_EAST_1),
    UsWest1 -> UnderlyingRegion.getRegion(UnderlyingRegions.US_WEST_1),
    UsWest2 -> UnderlyingRegion.getRegion(UnderlyingRegions.US_WEST_2),
    EuWest1 -> UnderlyingRegion.getRegion(UnderlyingRegions.EU_WEST_1),
    EuCentral1 -> UnderlyingRegion.getRegion(UnderlyingRegions.EU_CENTRAL_1),
    ApSouthEast1 -> UnderlyingRegion.getRegion(UnderlyingRegions.AP_SOUTHEAST_1),
    ApSouthEast2 -> UnderlyingRegion.getRegion(UnderlyingRegions.AP_SOUTHEAST_2),
    ApNorthEast1 -> UnderlyingRegion.getRegion(UnderlyingRegions.AP_NORTHEAST_1),
    SaEast1 -> UnderlyingRegion.getRegion(UnderlyingRegions.SA_EAST_1),
    CnNorth1 -> UnderlyingRegion.getRegion(UnderlyingRegions.CN_NORTH_1))

  valuesAndEnums.map { case (value, enum) =>
    s"The Region::apply($enum)" should {
      s"return $value" in {
        Region.apply(enum) should be (value)
      }
    }
  }

  valuesAndEnums.map { case (value, enum) =>
    s"The Region::underlying($value)" should {
      s"return $enum" in {
        Region.underlying(value) should be (enum)
      }
    }
  }
}
