package com.github.plokhotnyuk.jsoniter_scala.benchmark

import org.openjdk.jmh.annotations.Benchmark

class ArrayOfBooleansReading extends ArrayOfBooleansBenchmark {
  def avSystemGenCodec(): Array[Boolean] = {
    import com.avsystem.commons.serialization.json._
    import java.nio.charset.StandardCharsets.UTF_8

    JsonStringInput.read[Array[Boolean]](new String(jsonBytes, UTF_8))
  }

  def borer(): Array[Boolean] = {
    import io.bullet.borer.Json

    Json.decode(jsonBytes).to[Array[Boolean]].value
  }

  def circe(): Array[Boolean] = {
    import io.circe.jawn._

    decodeByteArray[Array[Boolean]](jsonBytes).fold(throw _, identity)
  }

  def circeJsoniter(): Array[Boolean] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.CirceJsoniterCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.core._
    import io.circe.Decoder

    Decoder[Array[Boolean]].decodeJson(readFromArray(jsonBytes)).fold(throw _, identity)
  }

  def dslJsonScala(): Array[Boolean] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.DslPlatformJson._

    dslJsonDecode[Array[Boolean]](jsonBytes)
  }

  def jacksonScala(): Array[Boolean] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.JacksonSerDesers._

    jacksonMapper.readValue[Array[Boolean]](jsonBytes)
  }

  def json4sJackson(): Array[Boolean] = {
    import org.json4s._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.Json4sJacksonMappers._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.CommonJson4sFormats._

    mapper.readValue[JValue](jsonBytes, jValueType).extract[Array[Boolean]]
  }

  def json4sNative(): Array[Boolean] = {
    import org.json4s._
    import org.json4s.native.JsonMethods._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.CommonJson4sFormats._
    import java.nio.charset.StandardCharsets.UTF_8

    parse(new String(jsonBytes, UTF_8)).extract[Array[Boolean]]
  }

  def jsoniterScala(): Array[Boolean] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.JsoniterScalaCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.core._

    readFromArray[Array[Boolean]](jsonBytes)
  }

  def playJson(): Array[Boolean] = {
    import play.api.libs.json.Json

    Json.parse(jsonBytes).as[Array[Boolean]]
  }

  def playJsonJsoniter(): Array[Boolean] = {
    import com.evolutiongaming.jsonitertool.PlayJsonJsoniter._
    import com.github.plokhotnyuk.jsoniter_scala.core._

    readFromArray(jsonBytes).as[Array[Boolean]]
  }

  def smithy4sJson(): Array[Boolean] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.Smithy4sJCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.core._

    readFromArray[Array[Boolean]](jsonBytes)
  }

  def sprayJson(): Array[Boolean] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.SprayFormats._
    import spray.json._

    JsonParser(jsonBytes).convertTo[Array[Boolean]]
  }

  def uPickle(): Array[Boolean] = {
    import upickle.default._

    read[Array[Boolean]](jsonBytes)
  }

  def weePickle(): Array[Boolean] = {
    import com.rallyhealth.weejson.v1.jackson.FromJson
    import com.rallyhealth.weepickle.v1.WeePickle.ToScala

    FromJson(jsonBytes).transform(ToScala[Array[Boolean]])
  }

  def zioJson(): Array[Boolean] = {
    import zio.json.DecoderOps
    import java.nio.charset.StandardCharsets.UTF_8

    new String(jsonBytes, UTF_8).fromJson[Array[Boolean]].fold(sys.error, identity)
  }
}