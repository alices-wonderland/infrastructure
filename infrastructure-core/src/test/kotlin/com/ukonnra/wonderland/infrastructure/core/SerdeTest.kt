package com.ukonnra.wonderland.infrastructure.core

import com.ukonnra.wonderland.infrastructure.core.error.ExternalError
import com.ukonnra.wonderland.infrastructure.core.error.WonderlandError
import com.ukonnra.wonderland.infrastructure.testsuite.serdeCheck
import org.junit.jupiter.api.Test

class SerdeTest {
  @Test
  fun testExternalError() {
    serdeCheck(
      ExternalError(
        "error-code",
        mapOf("field-1" to "value-1", "field-2" to mapOf("meta-1" to "meta-1.1"), "field-3" to 100)
      ),
      """
        {
          "code" : "error-code",
          "data" : {
            "field-1" : "value-1",
            "field-2" : {
              "meta-1" : "meta-1.1"
            },
            "field-3" : 100
          },
          "message" : "<Unknown External Error>",
          "statusCode" : 400
        }
      """.trimIndent(),
    )
  }

  @Test
  fun testWonderlandErrors() {
    val values = mapOf(
      """
        {
          "@c" : ".WonderlandError${'$'}NotFound",
          "type" : "type",
          "id" : "id",
          "message" : "type[id] is not found",
          "statusCode" : 404
        }
      """.trimIndent() to
        WonderlandError.NotFound("type", "id"),
      """
        {
          "@c" : ".WonderlandError${'$'}AlreadyDeleted",
          "type" : "type",
          "id" : "id",
          "message" : "type[id] already deleted",
          "statusCode" : 400
        }
      """.trimIndent() to WonderlandError.AlreadyDeleted("type", "id"),
      """
        {
          "@c" : ".WonderlandError${'$'}AlreadyExists",
          "type" : "type",
          "id" : "id",
          "message" : "type[id] already exists",
          "statusCode" : 409
        }
      """.trimIndent() to WonderlandError.AlreadyExists("type", "id"),
    )

    for ((expected, value) in values) {
      serdeCheck(value, expected)
    }
  }
}
