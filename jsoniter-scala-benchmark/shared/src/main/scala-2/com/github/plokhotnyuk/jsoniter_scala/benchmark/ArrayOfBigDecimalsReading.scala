package com.github.plokhotnyuk.jsoniter_scala.benchmark

import org.openjdk.jmh.annotations.Benchmark

class ArrayOfBigDecimalsReading extends ArrayOfBigDecimalsBenchmark {
  def avSystemGenCodec(): Array[BigDecimal] = {
    import com.avsystem.commons.serialization.json._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.AVSystemCodecs._
    import java.nio.charset.StandardCharsets.UTF_8

    JsonStringInput.read[Array[BigDecimal]](new String(jsonBytes, UTF_8), jsonOptions)
  }

  def borer(): Array[BigDecimal] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.BorerJsonEncodersDecoders.decodingConfig
    import io.bullet.borer.Json

    Json.decode(jsonBytes).withConfig(decodingConfig).to[Array[BigDecimal]].value
  }

  def circe(): Array[BigDecimal] = {
    import io.circe.jawn._

    decodeByteArray[Array[BigDecimal]](jsonBytes).fold(throw _, identity)
  }

  def circeJsoniter(): Array[BigDecimal] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.CirceJsoniterCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.core._
    import io.circe.Decoder

    Decoder[Array[BigDecimal]].decodeJson(readFromArray(jsonBytes)).fold(throw _, identity)
  }

  def dslJsonScala(): Array[BigDecimal] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.DslPlatformJson._

    dslJsonDecode[Array[BigDecimal]](jsonBytes)
  }

  def jacksonScala(): Array[BigDecimal] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.JacksonSerDesers._

    jacksonMapper.readValue[Array[BigDecimal]](jsonBytes)
  }

  def json4sJackson(): Array[BigDecimal] = {
    import org.json4s._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.Json4sJacksonMappers._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.CommonJson4sFormats._

    bigNumberMapper.readValue[JValue](jsonBytes, jValueType).extract[Array[BigDecimal]]
  }

  def json4sNative(): Array[BigDecimal] = {
    import org.json4s._
    import org.json4s.native.JsonMethods._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.CommonJson4sFormats._
    import java.nio.charset.StandardCharsets.UTF_8

    parse(new String(jsonBytes, UTF_8), useBigDecimalForDouble = true).extract[Array[BigDecimal]]
  }

  def jsoniterScala(): Array[BigDecimal] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.JsoniterScalaCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.core._

    readFromArray[Array[BigDecimal]](jsonBytes)
  }

  def playJson(): Array[BigDecimal] = {
    import play.api.libs.json.Json

    Json.parse(jsonBytes).as[Array[BigDecimal]]
  }

  def playJsonJsoniter(): Array[BigDecimal] = {
    import com.evolutiongaming.jsonitertool.PlayJsonJsoniter._
    import com.github.plokhotnyuk.jsoniter_scala.core._

    readFromArray(jsonBytes).as[Array[BigDecimal]]
  }

  def smithy4sJson(): Array[BigDecimal] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.Smithy4sJCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.core._

    readFromArray[Array[BigDecimal]](jsonBytes)
  }

  def sprayJson(): Array[BigDecimal] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.SprayFormats._
    import spray.json._

    JsonParser(jsonBytes).convertTo[Array[BigDecimal]]
  }

  def uPickle(): Array[BigDecimal] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.UPickleReaderWriters._

    read[Array[BigDecimal]](jsonBytes)
  }

  def weePickle(): Array[BigDecimal] = {
    import com.rallyhealth.weejson.v1.jackson.FromJson
    import com.rallyhealth.weepickle.v1.WeePickle.ToScala

    FromJson(jsonBytes).transform(ToScala[Array[BigDecimal]])
  }

  def zioJson(): Array[BigDecimal] = {
    import zio.json._
    import java.nio.charset.StandardCharsets.UTF_8

    new String(jsonBytes, UTF_8).fromJson[Array[BigDecimal]].fold(sys.error, identity)
  }
}