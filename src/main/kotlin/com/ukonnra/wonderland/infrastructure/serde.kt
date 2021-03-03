package com.ukonnra.wonderland.infrastructure

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer
import org.springframework.http.HttpStatus

object HttpStatusSerializer : StdScalarSerializer<HttpStatus>(HttpStatus::class.java) {
  override fun serialize(value: HttpStatus, gen: JsonGenerator, provider: SerializerProvider) =
    gen.writeNumber(value.value())
}

object HttpStatusDeserializer : StdScalarDeserializer<HttpStatus>(HttpStatus::class.java) {
  override fun deserialize(p: JsonParser, ctxt: DeserializationContext) = HttpStatus.valueOf(p.numberValue as Int)
}
