package com.ukonnra.wonderland.infrastructure.testsuite

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import org.junit.jupiter.api.Assertions

inline fun <reified T> serdeCheck(value: T, expected: String) {
  val jsonMap = jacksonObjectMapper().convertValue(value, jacksonTypeRef<Map<String, Any>>())
  val expectedMap = jacksonObjectMapper().readValue(expected, jacksonTypeRef<Map<String, Any>>())
  Assertions.assertEquals(expectedMap, jsonMap)

  val obj: T = jacksonObjectMapper().convertValue(jsonMap, jacksonTypeRef())
  Assertions.assertEquals(value, obj)
}
