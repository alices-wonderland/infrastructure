package com.ukonnra.wonderland.infrastructure

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.ukonnra.wonderland.infrastructure.error.ExternalError
import com.ukonnra.wonderland.infrastructure.error.WonderlandError
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SerdeTest {
  @Test
  fun testExternalError() {
    doCheck(
      ExternalError(
        "error-code",
        mapOf("field-1" to "value-1", "field-2" to mapOf("meta-1" to "meta-1.1"), "field-3" to 100)
      ),
      """{"code":"error-code","data":{"field-1":"value-1","field-2":{"meta-1":"meta-1.1"},"field-3":100},"message":"<Unknown External Error>","statusCode":400}""",
    )
  }

  @Test
  fun testWonderlandErrors() {
    val values = mapOf(
      """{"@c":".WonderlandError${'$'}NotFound","type":"type","id":"id","statusCode":404,"message":"type[id] is not found"}""" to WonderlandError.NotFound("type", "id"),
      """{"@c":".WonderlandError${'$'}AlreadyDeleted","type":"type","id":"id","statusCode":400,"message":"type[id] already deleted"}""" to WonderlandError.AlreadyDeleted("type", "id"),
      """{"@c":".WonderlandError${'$'}AlreadyExists","type":"type","id":"id","statusCode":409,"message":"type[id] already exists"}""" to WonderlandError.AlreadyExists("type", "id"),
    )

    for ((expected, value) in values) {
      doCheck(value, expected)
    }
  }
}

private inline fun <reified T> doCheck(value: T, expected: String) {
  val jsonStr = jacksonObjectMapper().writeValueAsString(value)
  Assertions.assertEquals(expected, jsonStr)

  val obj: T = jacksonObjectMapper().readValue(jsonStr)
  Assertions.assertEquals(value, obj)
}
