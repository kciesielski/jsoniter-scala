package com.github.plokhotnyuk.jsoniter_scala.benchmark

import org.openjdk.jmh.annotations.Benchmark

class BigDecimalReading extends BigDecimalBenchmark {
  def borer(): BigDecimal = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.BorerJsonEncodersDecoders._
    import io.bullet.borer.Json

    Json.decode(jsonBytes).withConfig(decodingConfig).to[BigDecimal].value
  }

  def circe(): BigDecimal = {
    import io.circe.jawn._

    decodeByteArray[BigDecimal](jsonBytes).fold(throw _, identity)
  }

  def circeJsoniter(): BigDecimal = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.CirceJsoniterCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.core._
    import io.circe.Decoder

    Decoder[BigDecimal].decodeJson(readFromArray(jsonBytes)).fold(throw _, identity)
  }

  def jacksonScala(): BigDecimal = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.JacksonSerDesers._

    jacksonMapper.readValue[BigDecimal](jsonBytes)
  }

  @annotation.nowarn
  def json4sJackson(): BigDecimal = {
    import org.json4s._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.Json4sJacksonMappers._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.CommonJson4sFormats._

    bigNumberMapper.readValue[JValue](jsonBytes, jValueType).extract[BigDecimal]
  }
/* FIXME: json4s.native throws org.json4s.ParserUtil$ParseException: expected field or array
  @annotation.nowarn
  def json4sNative(): BigDecimal = {
    import org.json4s._
    import org.json4s.native.JsonMethods._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.CommonJson4sFormats._
    import java.nio.charset.StandardCharsets.UTF_8

    parse(new String(jsonBytes, UTF_8)).extract[BigDecimal]
  }
*/
  def jsoniterScala(): BigDecimal = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.JsoniterScalaCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.core._

    readFromArray[BigDecimal](jsonBytes)(bigDecimalCodec)
  }
/* FIXME: Play-JSON: don't know how to tune precision for parsing of BigDecimal values
  def playJson(): BigDecimal = {
    import play.api.libs.json.Json

    Json.parse(jsonBytes).as[BigDecimal]
  }
*/
/* FIXME: smithy4sJson: don't know how to tune precision for parsing of BigDecimal values
  def smithy4sJson(): BigDecimal = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.Smithy4sCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.core._

    readFromArray[BigDecimal](jsonBytes)(bigDecimalJCodec)
  }
*/
  def sprayJson(): BigDecimal = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.SprayFormats._
    import spray.json._

    JsonParser(jsonBytes, jsonParserSettings).convertTo[BigDecimal]
  }

  def uPickle(): BigDecimal = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.UPickleReaderWriters._

    read[BigDecimal](jsonBytes)
  }

  def weePickle(): BigDecimal = {
    import com.rallyhealth.weejson.v1.jackson.FromJson
    import com.rallyhealth.weepickle.v1.WeePickle.ToScala

    FromJson(jsonBytes).transform(ToScala[BigDecimal])
  }
}