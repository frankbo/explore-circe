package explore

import io.circe.Decoder.Result
import io.circe._
import io.circe.generic.semiauto._
import cats.implicits._

object EncodeDecoderExamples2 {
  final case class Data(key1: String, key2: Int)

  object Data {
    // Codec implements encode and decoder. They can also be implemented separately with Encoder[A] Decoder[A]
    implicit val codec: Codec[Data] = new Codec[Data] {
      override def apply(c: HCursor): Result[Data] =
        (c.downField("key1").as[String], c.downField("key2").as[Int])
          .mapN(Data.apply)

      override def apply(a: Data): Json = Json.obj(
        "key1" -> Json.fromString(a.key1),
        "key2" -> Json.fromInt(a.key2)
      )
    }

    /* Encoder and Decoder separated with */
    implicit val decoder: Decoder[Data] = c =>
      (c.get[String]("key1"), c.get[Int]("key2"))
        .mapN(apply)

    implicit val encoder: Encoder[Data] = a =>
      Json.obj(
        "key1" -> Json.fromString(a.key1),
        "key2" -> Json.fromInt(a.key2)
    )

    /* Another way is to use forProduct */
    implicit val anotherDecoder: Decoder[Data] =
      Decoder.forProduct2("key1", "key2")(apply)

    implicit val anotherEncoder: Encoder[Data] =
      Encoder.forProduct2("key1", "key2")(unapply(_).get)

    /* Another way with semiauto decoder and encoder. Benefit over the automated encoder and decoder is that it
    more specific, faster and safer then the manual ones.
     */
    implicit val semiautoDecoder: Decoder[Data] = deriveDecoder
    implicit val semiautoEncoder: Encoder[Data] = deriveEncoder

    final case class Key1(value: String) extends AnyVal
    object Key1 {
      implicit val decoder: Decoder[Key1] = Decoder[String].map(apply)
      implicit val encoder: Encoder[Key1] = v => Encoder[String].apply(v.value)
      /* Same encoder as above with contramap */
      implicit val encoder2: Encoder[Key1] =
        Encoder[String].contramap(_.value)
    }
  }
}
