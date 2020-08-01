package explore

import eu.timepit.refined.types.all._
import io.circe._
import io.circe.refined._

object RefinedDecoderExample {
  final case class RefinedData(key1: NonEmptyString, key2: Int)
  object RefinedData {
    implicit val decoder: Decoder[RefinedData] =
      Decoder.forProduct2("key1", "key2")(RefinedData.apply)
  }
}
