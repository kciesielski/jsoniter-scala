package com.github.plokhotnyuk.jsoniter_scala.benchmark

import org.openjdk.jmh.annotations.Benchmark

class GoogleMapsAPIPrettyPrinting extends GoogleMapsAPIBenchmark {
  def circe(): Array[Byte] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.CirceEncodersDecoders._
    import io.circe.syntax._
    import java.nio.charset.StandardCharsets.UTF_8

    prettyPrinter.print(obj.asJson).getBytes(UTF_8)
  }

  def circeJsoniter(): Array[Byte] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.CirceEncodersDecoders._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.CirceJsoniterCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.core._
    import io.circe.syntax._

    writeToArray(obj.asJson, prettyConfig)
  }

  def jacksonScala(): Array[Byte] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.JacksonSerDesers._

    jacksonPrettyMapper.writeValueAsBytes(obj)
  }

  def json4sJackson(): Array[Byte] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.CommonJson4sFormats._
    import org.json4s._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.Json4sJacksonMappers._

    prettyPrintMapper.writeValueAsBytes(Extraction.decompose(obj))
  }

  def json4sNative(): Array[Byte] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.CommonJson4sFormats._
    import org.json4s.native.Serialization._
    import java.nio.charset.StandardCharsets.UTF_8

    writePretty(obj).getBytes(UTF_8)
  }

  def jsoniterScala(): Array[Byte] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.JsoniterScalaCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.core._

    writeToArray(obj, prettyConfig)
  }

  def jsoniterScalaPrealloc(): Int = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.JsoniterScalaCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.core._

    writeToSubArray(obj, preallocatedBuf, 64, preallocatedBuf.length, prettyConfig)
  }

  def playJson(): Array[Byte] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.PlatformUtils._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.PlayJsonFormats._
    import play.api.libs.json.Json

    prettyPrintBytes(Json.toJson(obj))
  }

  def playJsonJsoniter(): Array[Byte] = {
    import com.evolutiongaming.jsonitertool.PlayJsonJsoniter._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.PlayJsonFormats._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.PlayJsonJsoniterFormats._
    import com.github.plokhotnyuk.jsoniter_scala.core._
    import play.api.libs.json.Json

    writeToArray(Json.toJson(obj), prettyConfig)
  }

  def smithy4sJson(): Array[Byte] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.Smithy4sJCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.core._

    writeToArray(obj, prettyConfig)
  }

  def sprayJson(): Array[Byte] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.SprayFormats._
    import spray.json._
    import java.nio.charset.StandardCharsets.UTF_8

    prettyPrinter(obj.toJson).getBytes(UTF_8)
  }

  def uPickle(): Array[Byte] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.UPickleReaderWriters._

    writeToByteArray(obj, 2)
  }

  def weePickle(): Array[Byte] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.WeePickleFromTos._
    import com.rallyhealth.weepickle.v1.WeePickle.FromScala

    FromScala(obj).transform(ToPrettyJson.bytes)
  }

  def zioJson(): Array[Byte] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.ZioJSONEncoderDecoders._
    import zio.json._
    import java.nio.charset.StandardCharsets.UTF_8

    obj.toJsonPretty.getBytes(UTF_8)
  }
}