package org.typedsolutions.aws.kinesis.model

import com.amazonaws.regions.{Regions => UnderlyingRegions, Region => UnderlyingRegion}
import com.amazonaws.ClientConfiguration

// TODO: Wrap ClientConfiguration.
case class CreateKinesisClient(
  region: Option[Region.Value] = None,
  endpoint: Option[String] = None,
  configuration: ClientConfiguration = new ClientConfiguration()) extends Command

object Region {
  sealed trait Value
  case object GovCloud extends Value
  case object UsEast1 extends Value
  case object UsWest1 extends Value
  case object UsWest2 extends Value
  case object EuWest1 extends Value
  case object EuCentral1 extends Value
  case object ApSouthEast1 extends Value
  case object ApSouthEast2 extends Value
  case object ApNorthEast1 extends Value
  case object SaEast1 extends Value
  case object CnNorth1 extends Value

  def underlying(value: Value): UnderlyingRegion = value match {
    case GovCloud => UnderlyingRegion.getRegion(UnderlyingRegions.GovCloud)
    case UsEast1 => UnderlyingRegion.getRegion(UnderlyingRegions.US_EAST_1)
    case UsWest1 => UnderlyingRegion.getRegion(UnderlyingRegions.US_WEST_1)
    case UsWest2 => UnderlyingRegion.getRegion(UnderlyingRegions.US_WEST_2)
    case EuWest1 => UnderlyingRegion.getRegion(UnderlyingRegions.EU_WEST_1)
    case EuCentral1 => UnderlyingRegion.getRegion(UnderlyingRegions.EU_CENTRAL_1)
    case ApSouthEast1 => UnderlyingRegion.getRegion(UnderlyingRegions.AP_SOUTHEAST_1)
    case ApSouthEast2 => UnderlyingRegion.getRegion(UnderlyingRegions.AP_SOUTHEAST_2)
    case ApNorthEast1 => UnderlyingRegion.getRegion(UnderlyingRegions.AP_NORTHEAST_1)
    case SaEast1 => UnderlyingRegion.getRegion(UnderlyingRegions.SA_EAST_1)
    case CnNorth1 => UnderlyingRegion.getRegion(UnderlyingRegions.CN_NORTH_1)
  }

  def apply(underlying: UnderlyingRegion): Value = UnderlyingRegions.fromName(underlying.getName) match {
    case UnderlyingRegions.GovCloud => GovCloud
    case UnderlyingRegions.US_EAST_1 => UsEast1
    case UnderlyingRegions.US_WEST_1 => UsWest1
    case UnderlyingRegions.US_WEST_2 => UsWest2
    case UnderlyingRegions.EU_WEST_1 => EuWest1
    case UnderlyingRegions.EU_CENTRAL_1 => EuCentral1
    case UnderlyingRegions.AP_SOUTHEAST_1 => ApSouthEast1
    case UnderlyingRegions.AP_SOUTHEAST_2 => ApSouthEast2
    case UnderlyingRegions.AP_NORTHEAST_1 => ApNorthEast1
    case UnderlyingRegions.SA_EAST_1 => SaEast1
    case UnderlyingRegions.CN_NORTH_1 => CnNorth1
  }
}
