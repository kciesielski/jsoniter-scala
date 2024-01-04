package com.github.plokhotnyuk.jsoniter_scala.benchmark

import com.github.plokhotnyuk.jsoniter_scala.benchmark.GoogleMapsAPI._
import org.openjdk.jmh.annotations.Benchmark

class GoogleMapsAPIReading extends GoogleMapsAPIBenchmark {
  def borer(): DistanceMatrix = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.BorerJsonEncodersDecoders._
    import io.bullet.borer.Json

    Json.decode(jsonBytes).to[DistanceMatrix].value
  }

  @Benchmark
  def circe(): DistanceMatrix = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.CirceEncodersDecoders._
    import io.circe.jawn._

    decodeByteArray[DistanceMatrix](jsonBytes).fold(throw _, identity)
  }

  def circeJsoniter(): DistanceMatrix = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.CirceEncodersDecoders._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.CirceJsoniterCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.core._
    import io.circe.Decoder

    Decoder[DistanceMatrix]
      .decodeJson(readFromArray(jsonBytes))
      .fold(throw _, identity)
  }

  def jacksonScala(): DistanceMatrix = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.JacksonSerDesers._

    jacksonMapper.readValue[DistanceMatrix](jsonBytes)
  }

  @annotation.nowarn
  def json4sJackson(): DistanceMatrix = {
    import org.json4s._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.Json4sJacksonMappers._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.CommonJson4sFormats._

    mapper.readValue[JValue](jsonBytes, jValueType).extract[DistanceMatrix]
  }

  @annotation.nowarn
  def json4sNative(): DistanceMatrix = {
    import org.json4s._
    import org.json4s.native.JsonMethods._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.CommonJson4sFormats._
    import java.nio.charset.StandardCharsets.UTF_8

    parse(new String(jsonBytes, UTF_8)).extract[DistanceMatrix]
  }

  @Benchmark
  def jsoniterScala(): DistanceMatrix = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.JsoniterScalaCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.core._

    readFromArray[DistanceMatrix](jsonBytes)
  }

  def playJson(): DistanceMatrix = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.PlayJsonFormats._
    import play.api.libs.json.Json

    Json.parse(jsonBytes).as[DistanceMatrix]
  }

  def playJsonJsoniter(): DistanceMatrix = {
    import com.evolutiongaming.jsonitertool.PlayJsonJsoniter._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.PlayJsonFormats._
    import com.github.plokhotnyuk.jsoniter_scala.core._

    readFromArray(jsonBytes).as[DistanceMatrix]
  }

  def smithy4sJson(): DistanceMatrix = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.Smithy4sJCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.core._

    readFromArray[DistanceMatrix](jsonBytes)
  }

  def sprayJson(): DistanceMatrix = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.SprayFormats._
    import spray.json._

    JsonParser(jsonBytes).convertTo[DistanceMatrix]
  }

  @Benchmark
  def uPickle(): DistanceMatrix = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.UPickleReaderWriters._

    read[DistanceMatrix](jsonBytes)
  }

  @Benchmark
  def uPickleAst(): DistanceMatrix = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.UPickleReaderWriters._

    readAst[DistanceMatrix](jsonBytes)
  }

  def weePickle(): DistanceMatrix = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.WeePickleFromTos._
    import com.rallyhealth.weejson.v1.jackson.FromJson
    import com.rallyhealth.weepickle.v1.WeePickle.ToScala

    FromJson(jsonBytes).transform(ToScala[DistanceMatrix])
  }

  def zioJson(): DistanceMatrix = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.ZioJSONEncoderDecoders._
    import zio.json._
    import zio.json.JsonDecoder._
    import java.nio.charset.StandardCharsets.UTF_8

    new String(jsonBytes, UTF_8)
      .fromJson[DistanceMatrix]
      .fold(sys.error, identity)
  }
}
