package com.github.plokhotnyuk.jsoniter_scala.benchmark

import org.openjdk.jmh.annotations.Benchmark

class StringOfNonAsciiCharsReading extends StringOfNonAsciiCharsBenchmark {
  def avSystemGenCodec(): String = {
    import com.avsystem.commons.serialization.json._
    import java.nio.charset.StandardCharsets.UTF_8

    JsonStringInput.read[String](new String(jsonBytes, UTF_8))
  }

  def borer(): String = {
    import io.bullet.borer.Json

    Json.decode(jsonBytes).to[String].value
  }

  def circe(): String = {
    import io.circe.jawn._

    decodeByteArray[String](jsonBytes).fold(throw _, identity)
  }

  def circeJsoniter(): String = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.CirceJsoniterCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.core._
    import io.circe.Decoder

    Decoder[String].decodeJson(readFromArray(jsonBytes, tooLongStringConfig)).fold(throw _, identity)
  }

  def dslJsonScala(): String = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.DslPlatformJson._

    dslJsonDecode[String](jsonBytes)(stringDecoder)
  }

  def jacksonScala(): String = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.JacksonSerDesers._

    jacksonMapper.readValue[String](jsonBytes)
  }

  def json4sJackson(): String = {
    import org.json4s._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.Json4sJacksonMappers._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.CommonJson4sFormats._

    mapper.readValue[JValue](jsonBytes, jValueType).extract[String]
  }
/* FIXME: json4s.native throws org.json4s.ParserUtil$ParseException: expected field or array
  def json4sNative(): String = {
    import org.json4s._
    import org.json4s.native.JsonMethods._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.CommonJson4sFormats._
    import java.nio.charset.StandardCharsets.UTF_8

    parse(new String(jsonBytes, UTF_8)).extract[String]
  }
*/
  def jsoniterScala(): String = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.JsoniterScalaCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.core._

    readFromArray[String](jsonBytes, tooLongStringConfig)(stringCodec)
  }

  def playJson(): String = {
    import play.api.libs.json.Json

    Json.parse(jsonBytes).as[String]
  }

  def playJsonJsoniter(): String = {
    import com.evolutiongaming.jsonitertool.PlayJsonJsoniter._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.PlayJsonJsoniterFormats._
    import com.github.plokhotnyuk.jsoniter_scala.core._

    readFromArray[play.api.libs.json.JsValue](jsonBytes, tooLongStringConfig).as[String]
  }

  def smithy4sJson(): String = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.Smithy4sJCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.core._

    readFromArray[String](jsonBytes, tooLongStringConfig)(stringJCodec)
  }

  def sprayJson(): String = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.SprayFormats._
    import spray.json._

    JsonParser(jsonBytes).convertTo[String]
  }

  def uPickle(): String = {
    import upickle.default._

    read[String](jsonBytes)
  }

  def weePickle(): String = {
    import com.rallyhealth.weejson.v1.jackson.FromJson
    import com.rallyhealth.weepickle.v1.WeePickle.ToScala

    FromJson(jsonBytes).transform(ToScala[String])
  }

  def zioJson(): String = {
    import zio.json.DecoderOps
    import java.nio.charset.StandardCharsets.UTF_8

    new String(jsonBytes, UTF_8).fromJson[String].fold(sys.error, identity)
  }
}