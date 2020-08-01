package explore

import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import io.circe.literal._

object EncoderDecoderExample {
  final case class Person(name: String, age: Int)

  def encodePersonWithApply(person: Person): Json =
    Encoder[Person].apply(person)

  def encodePersonWithSyntaxImport(person: Person): Json =
    person.asJson

  def decodeStringToPerson(personAsString: String): Either[Error, Person] =
    decode[Person](personAsString)

  def decodeStringToPersonWithParser(
      personAsString: String): Either[Error, Person] =
    parse(personAsString).flatMap(_.as[Person])

  def decodeFromHCursor(personAsString: String): Either[Error, Person] =
    parse(personAsString).map(HCursor.fromJson).flatMap(_.as[Person])

  def caseClassToJsonAndBackToPerson(person: Person): Either[Error, Person] =
    json"""$person""".as[Person]

  def decodeFromJsonResource(path: String): Either[Throwable, Person] = {
    ReadJsonFrom.resourceInto[Person](resource = path)
  }
}
