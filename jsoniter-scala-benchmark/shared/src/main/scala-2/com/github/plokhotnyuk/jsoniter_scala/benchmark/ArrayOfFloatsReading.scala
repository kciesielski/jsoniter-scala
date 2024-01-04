package com.github.plokhotnyuk.jsoniter_scala.benchmark

import org.openjdk.jmh.annotations.Benchmark

class ArrayOfFloatsReading extends ArrayOfFloatsBenchmark {
  def avSystemGenCodec(): Array[Float] = {
    import com.avsystem.commons.serialization.json._
    import java.nio.charset.StandardCharsets.UTF_8

    JsonStringInput.read[Array[Float]](new String(jsonBytes, UTF_8))
  }

  def borer(): Array[Float] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.BorerJsonEncodersDecoders._
    import io.bullet.borer.Json

    Json.decode(jsonBytes).withConfig(decodingConfig).to[Array[Float]].value
  }

  def circe(): Array[Float] = {
    import io.circe.jawn._

    decodeByteArray[Array[Float]](jsonBytes).fold(throw _, identity)
  }

  def circeJsoniter(): Array[Float] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.CirceJsoniterCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.core._
    import io.circe.Decoder

    Decoder[Array[Float]].decodeJson(readFromArray(jsonBytes)).fold(throw _, identity)
  }
/* FIXME: DSL-JSON parses 1.199999988079071 as 1.2f instead of 1.1999999f
  def dslJsonScala(): Array[Float] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.DslPlatformJson._

    dslJsonDecode[Array[Float]](jsonBytes)
  }
*/
  def jacksonScala(): Array[Float] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.JacksonSerDesers._

    jacksonMapper.readValue[Array[Float]](jsonBytes)
  }

  def json4sJackson(): Array[Float] = {
    import org.json4s._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.Json4sJacksonMappers._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.BigDecimalJson4sFormat._

    bigNumberMapper.readValue[JValue](jsonBytes, jValueType).extract[Array[Float]]
  }

  def json4sNative(): Array[Float] = {
    import org.json4s._
    import org.json4s.native.JsonMethods._
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.BigDecimalJson4sFormat._
    import java.nio.charset.StandardCharsets.UTF_8

    parse(new String(jsonBytes, UTF_8), useBigDecimalForDouble = true).extract[Array[Float]]
  }

  def jsoniterScala(): Array[Float] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.JsoniterScalaCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.core._

    readFromArray[Array[Float]](jsonBytes)
  }

  def playJson(): Array[Float] = {
    import play.api.libs.json.Json

    Json.parse(jsonBytes).as[Array[Float]]
  }

  def playJsonJsoniter(): Array[Float] = {
    import com.evolutiongaming.jsonitertool.PlayJsonJsoniter._
    import com.github.plokhotnyuk.jsoniter_scala.core._

    readFromArray[play.api.libs.json.JsValue](jsonBytes).as[Array[Float]]
  }

  def smithy4sJson(): Array[Float] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.Smithy4sJCodecs._
    import com.github.plokhotnyuk.jsoniter_scala.core._

    readFromArray[Array[Float]](jsonBytes)
  }

  def sprayJson(): Array[Float] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.SprayFormats._
    import spray.json._

    JsonParser(jsonBytes).convertTo[Array[Float]]
  }

  def uPickle(): Array[Float] = {
    import com.github.plokhotnyuk.jsoniter_scala.benchmark.UPickleReaderWriters._

    read[Array[Float]](jsonBytes)
  }

  def weePickle(): Array[Float] = {
    import com.rallyhealth.weejson.v1.jackson.FromJson
    import com.rallyhealth.weepickle.v1.WeePickle.ToScala

    FromJson(jsonBytes).transform(ToScala[Array[Float]])
  }

  def zioJson(): Array[Float] = {
    import zio.json.DecoderOps
    import java.nio.charset.StandardCharsets.UTF_8

    new String(jsonBytes, UTF_8).fromJson[Array[Float]].fold(sys.error, identity)
  }
}