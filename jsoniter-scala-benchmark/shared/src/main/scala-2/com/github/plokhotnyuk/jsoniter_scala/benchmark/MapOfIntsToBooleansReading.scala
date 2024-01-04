package com.github.plokhotnyuk.jsoniter_scala.benchmark

import org.openjdk.jmh.annotations.Benchmark

class MapOfIntsToBooleansReading extends MapOfIntsToBooleansBenchmark {
  def avSystemGenCodec(): Map[Int, Boolean] = {
    import com.avsystem.commons.serialization.json._
    import java.nio.charset.StandardCharsets.UTF_8

    JsonStringInput.read[Map[Int, Boolean]](new String(jsonBytes, UTF_8))
  }

  def circe(): Map[Int, Boolean] = {
    import io.circe.jawn._

    decodeByteArray[Map[Int, Boolean]](jsonBytes).fold(throw _, identity)
  }

  def circeJsoniter(): Map[Int, Boolean] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.CirceJsoniterCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.core._
    import io.circe.Decoder

    Decoder[Map[Int, Boolean]].decodeJson(readFromArray(jsonBytes)).fold(throw _, identity)
  }

  def dslJsonScala(): Map[Int, Boolean] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.DslPlatformJson._

    dslJsonDecode[Map[Int, Boolean]](jsonBytes)
  }

  def jacksonScala(): Map[Int, Boolean] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.JacksonSerDesers._

    jacksonMapper.readValue[Map[Int, Boolean]](jsonBytes)
  }

  def json4sJackson(): Map[Int, Boolean] = {
    import org.json4s._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.Json4sJacksonMappers._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.CommonJson4sFormats._

    mapper.readValue[JValue](jsonBytes, jValueType).extract[Map[Int, Boolean]]
  }

  def json4sNative(): Map[Int, Boolean] = {
    import org.json4s._
    import org.json4s.native.JsonMethods._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.CommonJson4sFormats._
    import java.nio.charset.StandardCharsets.UTF_8

    parse(new String(jsonBytes, UTF_8)).extract[Map[Int, Boolean]]
  }

  def jsoniterScala(): Map[Int, Boolean] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.JsoniterScalaCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.core._

    readFromArray[Map[Int, Boolean]](jsonBytes)
  }

  def playJson(): Map[Int, Boolean] = {
    import play.api.libs.json.Json

    Json.parse(jsonBytes).as[Map[Int, Boolean]]
  }

  def playJsonJsoniter(): Map[Int, Boolean] = {
    import com.evolutiongaming.jsonitertool.PlayJsonJsoniter._
    import com.github.plokhotnyuk.jsoniter_scala.core._

    readFromArray[play.api.libs.json.JsValue](jsonBytes).as[Map[Int, Boolean]]
  }

  def smithy4sJson(): Map[Int, Boolean] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.Smithy4sJCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.core._

    readFromArray[Map[Int, Boolean]](jsonBytes)
  }
/* FIXME: Spray-JSON throws spray.json.DeserializationException: Expected Int as JsNumber, but got "-1"
  def sprayJson(): Map[Int, Boolean] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.SprayFormats._
    import spray.json._

    JsonParser(jsonBytes).convertTo[Map[Int, Boolean]]
  }
*/
/* FIXME: uPickle parses maps from JSON arrays only
  def uPickle(): Map[Int, Boolean] = {
    import upickle.default._

    read[Map[Int, Boolean]](jsonBytes)
  }
*/
  def weePickle(): Map[Int, Boolean] = {
    import com.rallyhealth.weejson.v1.jackson.FromJson
    import com.rallyhealth.weepickle.v1.WeePickle.ToScala

    FromJson(jsonBytes).transform(ToScala[Map[Int, Boolean]])
  }

  def zioJson(): Map[Int, Boolean] = {
    import zio.json._
    import java.nio.charset.StandardCharsets.UTF_8

    new String(jsonBytes, UTF_8).fromJson[Map[Int, Boolean]].fold(sys.error, identity)
  }
}