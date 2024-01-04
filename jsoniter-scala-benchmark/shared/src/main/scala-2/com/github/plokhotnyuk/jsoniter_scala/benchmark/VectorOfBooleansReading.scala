package com.github.plokhotnyuk.jsoniter_scala.benchmark

import org.openjdk.jmh.annotations.Benchmark

class VectorOfBooleansReading extends VectorOfBooleansBenchmark {
  def avSystemGenCodec(): Vector[Boolean] = {
    import com.avsystem.commons.serialization.json._
    import java.nio.charset.StandardCharsets.UTF_8

    JsonStringInput.read[Vector[Boolean]](new String(jsonBytes, UTF_8))
  }

  def borer(): Vector[Boolean] = {
    import io.bullet.borer.Json

    Json.decode(jsonBytes).to[Vector[Boolean]].value
  }

  def circe(): Vector[Boolean] = {
    import io.circe.jawn._

    decodeByteArray[Vector[Boolean]](jsonBytes).fold(throw _, identity)
  }

  def circeJsoniter(): Vector[Boolean] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.CirceJsoniterCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.core._
    import io.circe.Decoder

    Decoder[Vector[Boolean]].decodeJson(readFromArray(jsonBytes)).fold(throw _, identity)
  }

  def dslJsonScala(): Vector[Boolean] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.DslPlatformJson._

    dslJsonDecode[Vector[Boolean]](jsonBytes)
  }

  def jacksonScala(): Vector[Boolean] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.JacksonSerDesers._

    jacksonMapper.readValue[Vector[Boolean]](jsonBytes)
  }

  def json4sJackson(): Vector[Boolean] = {
    import org.json4s._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.Json4sJacksonMappers._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.CommonJson4sFormats._

    mapper.readValue[JValue](jsonBytes, jValueType).extract[Vector[Boolean]]
  }

  def json4sNative(): Vector[Boolean] = {
    import org.json4s._
    import org.json4s.native.JsonMethods._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.CommonJson4sFormats._
    import java.nio.charset.StandardCharsets.UTF_8

    parse(new String(jsonBytes, UTF_8)).extract[Vector[Boolean]]
  }

  def jsoniterScala(): Vector[Boolean] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.JsoniterScalaCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.core._

    readFromArray[Vector[Boolean]](jsonBytes)
  }

  def playJson(): Vector[Boolean] = {
    import play.api.libs.json.Json

    Json.parse(jsonBytes).as[Vector[Boolean]]
  }

  def playJsonJsoniter(): Vector[Boolean] = {
    import com.evolutiongaming.jsonitertool.PlayJsonJsoniter._
    import com.github.plokhotnyuk.jsoniter_scala.core._

    readFromArray(jsonBytes).as[Vector[Boolean]]
  }

  def smithy4sJson(): Vector[Boolean] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.Smithy4sJCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.core._

    readFromArray[Vector[Boolean]](jsonBytes)
  }

  def sprayJson(): Vector[Boolean] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.SprayFormats._
    import spray.json._

    JsonParser(jsonBytes).convertTo[Vector[Boolean]]
  }

  def uPickle(): Vector[Boolean] = {
    import upickle.default._

    read[Vector[Boolean]](jsonBytes)
  }

  def weePickle(): Vector[Boolean] = {
    import com.rallyhealth.weejson.v1.jackson.FromJson
    import com.rallyhealth.weepickle.v1.WeePickle.ToScala

    FromJson(jsonBytes).transform(ToScala[Vector[Boolean]])
  }

  def zioJson(): Vector[Boolean] = {
    import zio.json.DecoderOps
    import java.nio.charset.StandardCharsets.UTF_8

    new String(jsonBytes, UTF_8).fromJson[Vector[Boolean]].fold(sys.error, identity)
  }
}