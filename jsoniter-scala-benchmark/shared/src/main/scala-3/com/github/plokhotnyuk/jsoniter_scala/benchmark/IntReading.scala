package com.github.plokhotnyuk.jsoniter_scala.benchmark

import org.openjdk.jmh.annotations.Benchmark

class IntReading extends IntBenchmark {
  def borer(): Int = {
    import io.bullet.borer.Json

    Json.decode(jsonBytes).to[Int].value
  }

  def circe(): Int = {
    import io.circe.jawn._

    decodeByteArray[Int](jsonBytes).fold(throw _, identity)
  }

  def circeJsoniter(): Int = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.CirceJsoniterCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.core._
    import io.circe.Decoder

    Decoder[Int].decodeJson(readFromArray(jsonBytes)).fold(throw _, identity)
  }

  def jacksonScala(): Int = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.JacksonSerDesers._

    jacksonMapper.readValue[Int](jsonBytes)
  }

  @annotation.nowarn
  def json4sJackson(): Int = {
    import org.json4s._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.Json4sJacksonMappers._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.CommonJson4sFormats._

    mapper.readValue[JValue](jsonBytes, jValueType).extract[Int]
  }
/* FIXME: json4s.native throws org.json4s.ParserUtil$ParseException: expected field or array
  @annotation.nowarn
  def json4sNative(): Int = {
    import org.json4s._
    import org.json4s.native.JsonMethods._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.CommonJson4sFormats._
    import java.nio.charset.StandardCharsets.UTF_8

    parse(new String(jsonBytes, UTF_8)).extract[Int]
  }
*/
  def jsoniterScala(): Int = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.JsoniterScalaCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.core._

    readFromArray[Int](jsonBytes)(intCodec)
  }

  def playJson(): Int = {
    import play.api.libs.json.Json

    Json.parse(jsonBytes).as[Int]
  }

  def playJsonJsoniter(): Int = {
    import com.evolutiongaming.jsonitertool.PlayJsonJsoniter._
    import com.github.plokhotnyuk.jsoniter_scala.core._

    readFromArray(jsonBytes).as[Int]
  }

  def smithy4sJson(): Int = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.Smithy4sJCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.core._

    readFromArray[Int](jsonBytes)(intJCodec)
  }

  def sprayJson(): Int = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.SprayFormats._
    import spray.json._

    JsonParser(jsonBytes).convertTo[Int]
  }

  def uPickle(): Int = {
    import upickle.default._

    read[Int](jsonBytes)
  }

  def weePickle(): Int = {
    import com.rallyhealth.weejson.v1.jackson.FromJson
    import com.rallyhealth.weepickle.v1.WeePickle.ToScala

    FromJson(jsonBytes).transform(ToScala[Int])
  }

  def zioJson(): Int = {
    import java.nio.charset.StandardCharsets.UTF_8
    import zio.json.DecoderOps

    new String(jsonBytes, UTF_8).fromJson[Int].fold(sys.error, identity)
  }
}