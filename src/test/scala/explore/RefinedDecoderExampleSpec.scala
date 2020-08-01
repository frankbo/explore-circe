package explore

import eu.timepit.refined.types.string._
import explore.RefinedDecoderExample._
import io.circe.DecodingFailure
import io.circe.parser._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class RefinedDecoderExampleSpec extends AnyFlatSpec with Matchers {
  it should "be able to decode refined types" in {
    val rawData = """
{
  "key1": "some string",
  "key2": 12
}
"""
    val refinedData = decode[RefinedData](rawData).toOption.get
    refinedData shouldBe RefinedData(NonEmptyString
                                       .from("some string")
                                       .toOption
                                       .get,
                                     12)
  }

  it should "be able to fail decoding when refined types are incorrect" in {
    val rawData = """
{
  "key1": "",
  "key2": 12
}
"""
    val refinedData = decode[RefinedData](rawData)
    refinedData.isInstanceOf[Left[DecodingFailure, RefinedData]] shouldBe true
  }
}
