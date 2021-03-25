package com.ukonnra.wonderland.infrastructure.core.viewstore

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import com.ukonnra.wonderland.infrastructure.core.Aggregate
import com.ukonnra.wonderland.infrastructure.core.Cursor
import com.ukonnra.wonderland.infrastructure.core.Identifier
import com.ukonnra.wonderland.infrastructure.core.error.WonderlandError
import java.time.Instant
import java.time.format.DateTimeParseException

private fun <A : Aggregate<ID>, ID : Identifier<ID>> A.toCursor(mapper: ObjectMapper, field: String): Cursor =
  when (val value = mapper.convertValue<Map<String, Any>>(this)[field]) {
    is String ->
      try {
        Cursor.Date(id, Instant.parse(value))
      } catch (e: DateTimeParseException) {
        Cursor.Str(id, value)
      }
    is Number -> Cursor.Num(id, value)
    else -> throw WonderlandError.FieldNotSortable(companion.type, field)
  }

data class CursorEdge<A : Aggregate<ID>, ID : Identifier<ID>>(val node: A, val cursor: Cursor) {
  constructor(mapper: ObjectMapper, node: A, field: String) : this(node, node.toCursor(mapper, field))
}
