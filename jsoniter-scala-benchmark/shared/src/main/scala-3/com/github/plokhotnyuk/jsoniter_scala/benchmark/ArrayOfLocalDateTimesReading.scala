package com.github.plokhotnyuk.jsoniter_scala.benchmark

import org.openjdk.jmh.annotations.Benchmark
import java.time.LocalDateTime

class ArrayOfLocalDateTimesReading extends ArrayOfLocalDateTimesBenchmark {
  def borer(): Array[LocalDateTime] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.BorerJsonEncodersDecoders._
    import io.bullet.borer.Json

    Json.decode(jsonBytes).to[Array[LocalDateTime]].value
  }

  def circe(): Array[LocalDateTime] = {
    import io.circe.jawn._

    decodeByteArray[Array[LocalDateTime]](jsonBytes).fold(throw _, identity)
  }

  def circeJsoniter(): Array[LocalDateTime] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.CirceJsoniterCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.circe.CirceCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.core._
    import io.circe.Decoder

    Decoder[Array[LocalDateTime]].decodeJson(readFromArray(jsonBytes)).fold(throw _, identity)
  }

  def jacksonScala(): Array[LocalDateTime] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.JacksonSerDesers._

    jacksonMapper.readValue[Array[LocalDateTime]](jsonBytes)
  }

  @annotation.nowarn
  def json4sJackson(): Array[LocalDateTime] = {
    import org.json4s._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.Json4sJacksonMappers._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.JavaTimeJson4sFormats._

    mapper.readValue[JValue](jsonBytes, jValueType).extract[Array[LocalDateTime]]
  }

  @annotation.nowarn
  def json4sNative(): Array[LocalDateTime] = {
    import org.json4s._
    import org.json4s.native.JsonMethods._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.JavaTimeJson4sFormats._
    import java.nio.charset.StandardCharsets.UTF_8

    parse(new String(jsonBytes, UTF_8)).extract[Array[LocalDateTime]]
  }

  def jsoniterScala(): Array[LocalDateTime] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.JsoniterScalaCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.core._

    readFromArray[Array[LocalDateTime]](jsonBytes)
  }

  def playJson(): Array[LocalDateTime] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.PlayJsonFormats._
    import play.api.libs.json.Json

    Json.parse(jsonBytes).as[Array[LocalDateTime]]
  }

  def playJsonJsoniter(): Array[LocalDateTime] = {
    import com.evolutiongaming.jsonitertool.PlayJsonJsoniter._
    import com.github.plokhotnyuk.jsoniter_scala.core._

    readFromArray(jsonBytes).as[Array[LocalDateTime]]
  }

  def sprayJson(): Array[LocalDateTime] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.SprayFormats._
    import spray.json._

    JsonParser(jsonBytes).convertTo[Array[LocalDateTime]]
  }

  def uPickle(): Array[LocalDateTime] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.UPickleReaderWriters._

    read[Array[LocalDateTime]](jsonBytes)
  }

  def weePickle(): Array[LocalDateTime] = {
    import com.rallyhealth.weejson.v1.jackson.FromJson
    import com.rallyhealth.weepickle.v1.WeePickle.ToScala

    FromJson(jsonBytes).transform(ToScala[Array[LocalDateTime]])
  }

  def zioJson(): Array[LocalDateTime] = {
    import zio.json.DecoderOps
    import java.nio.charset.StandardCharsets.UTF_8

    new String(jsonBytes, UTF_8).fromJson[Array[LocalDateTime]].fold(sys.error, identity)
  }
}