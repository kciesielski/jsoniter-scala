package com.github.plokhotnyuk.jsoniter_scala.benchmark

import org.openjdk.jmh.annotations.Benchmark

class ArrayOfBigIntsReading extends ArrayOfBigIntsBenchmark {
  def borer(): Array[BigInt] = {
    import io.bullet.borer.Json
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.BorerJsonEncodersDecoders._

    Json.decode(jsonBytes).withConfig(decodingConfig).to[Array[BigInt]].value
  }

  def circe(): Array[BigInt] = {
    import io.circe.jawn._

    decodeByteArray[Array[BigInt]](jsonBytes).fold(throw _, identity)
  }

  def circeJsoniter(): Array[BigInt] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.CirceJsoniterCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.circe.CirceCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.core._
    import io.circe.Decoder

    Decoder[Array[BigInt]].decodeJson(readFromArray(jsonBytes)).fold(throw _, identity)
  }

  def jacksonScala(): Array[BigInt] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.JacksonSerDesers._

    jacksonMapper.readValue[Array[BigInt]](jsonBytes)
  }

  @annotation.nowarn
  def json4sJackson(): Array[BigInt] = {
    import org.json4s._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.Json4sJacksonMappers._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.CommonJson4sFormats._

    bigNumberMapper.readValue[JValue](jsonBytes, jValueType).extract[Array[BigInt]]
  }

  @annotation.nowarn
  def json4sNative(): Array[BigInt] = {
    import org.json4s._
    import org.json4s.native.JsonMethods._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.CommonJson4sFormats._
    import java.nio.charset.StandardCharsets.UTF_8

    parse(new String(jsonBytes, UTF_8)).extract[Array[BigInt]]
  }

  def jsoniterScala(): Array[BigInt] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.JsoniterScalaCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.core._

    readFromArray[Array[BigInt]](jsonBytes)
  }
/* FIXME: Play-JSON looses significant digits in BigInt values
  def playJson(): Array[BigInt] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.PlayJsonFormats._
    import play.api.libs.json.Json

    Json.parse(jsonBytes).as[Array[BigInt]]
  }
*/
  def smithy4sJson(): Array[BigInt] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.Smithy4sJCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.core._

    readFromArray[Array[BigInt]](jsonBytes)
  }

  def sprayJson(): Array[BigInt] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.SprayFormats._
    import spray.json._

    JsonParser(jsonBytes).convertTo[Array[BigInt]]
  }

  def uPickle(): Array[BigInt] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.UPickleReaderWriters._

    read[Array[BigInt]](jsonBytes)
  }

  def weePickle(): Array[BigInt] = {
    import com.rallyhealth.weejson.v1.jackson.FromJson
    import com.rallyhealth.weepickle.v1.WeePickle.ToScala

    FromJson(jsonBytes).transform(ToScala[Array[BigInt]])
  }

  def zioJson(): Array[BigInt] = {
    import zio.json._
    import java.nio.charset.StandardCharsets.UTF_8

    new String(jsonBytes, UTF_8).fromJson[Array[BigInt]].fold(sys.error, identity)
  }
}