package explore

import explore.Examples._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ExamplesSpec extends AnyFlatSpec with Matchers {
  val tom: Person = Person("Tom", 25)
  val tomAsString: String = """{
    |  "name" : "Tom",
    |  "age" : 25
    |}""".stripMargin

  it should "encode a person to a json" in {
    encodePersonWithApply(tom).toString() shouldBe tomAsString
    encodePersonWithSyntaxImport(tom).toString() shouldBe tomAsString
  }

  it should "decode a valid string to a Person" in {
    decodeStringToPerson(tomAsString) shouldBe Right(tom)
    decodeStringToPersonWithParser(tomAsString) shouldBe Right(tom)
  }

  it should "decode an invalid string returns an error" in {
    val tomAsBrokenString = """{
      "name" : "Tom,
      "age" : 25
    }"""
    val brokenDecoding = decodeStringToPerson(tomAsBrokenString)
    brokenDecoding.fold(_ => "failed", identity) shouldBe "failed"
  }

  it should "decode a valid string to a Person with HCursor in between" in {
    decodeFromHCursor(tomAsString) shouldBe Right(tom)
  }

  it should "decode and encode through circe literals" in {
    caseClassToJsonAndBackToPerson(tom) shouldBe Right(tom)
  }

  it should "read a json resource file and decode it to a person" in {
    decodeFromJsonResource("data.json") shouldBe Right(Person("Tim", 22))
  }
}
