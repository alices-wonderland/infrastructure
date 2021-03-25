package com.ukonnra.wonderland.infrastructure.error

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.ukonnra.wonderland.infrastructure.FilterItem
import org.springframework.http.HttpStatus

@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS)
sealed class WonderlandError(override val message: String, override val cause: Throwable?) : AbstractError
  (message, cause) {
  data class NotFound(val type: String, val id: String, override val cause: Throwable? = null) :
    WonderlandError("$type[$id] is not found", cause) {
    override val statusCode: HttpStatus
      get() = HttpStatus.NOT_FOUND
  }

  data class AlreadyDeleted(val type: String, val id: String, override val cause: Throwable? = null) :
    WonderlandError("$type[$id] already deleted", cause)

  data class AlreadyExists(val type: String, val id: String, override val cause: Throwable? = null) :
    WonderlandError("$type[$id] already exists", cause) {
    override val statusCode: HttpStatus
      get() = HttpStatus.CONFLICT
  }

  data class NonNegativeParam(val paramName: String, override val cause: Throwable? = null) : WonderlandError
    ("`$paramName` should not be negative", cause)

  data class FieldNotSortable(val type: String, val field: String, override val cause: Throwable? = null) :
    WonderlandError("`$field` on $type is not sortable", cause) {
    override val statusCode: HttpStatus
      get() = HttpStatus.FORBIDDEN
  }

  data class InvalidFilterItem(val type: String, val filterItem: FilterItem, override val cause: Throwable? = null) :
    WonderlandError("$type cannot handle the filter item: $filterItem", cause) {
    override val statusCode: HttpStatus
      get() = HttpStatus.FORBIDDEN
  }

  data class NoAuth(val userId: String? = null, override val cause: Throwable? = null) :
    WonderlandError("Invalid identifier or password for User[$userId]", cause) {
    override val statusCode: HttpStatus
      get() = HttpStatus.UNAUTHORIZED
  }

  data class UpdateNothing(val type: String, val id: String, override val cause: Throwable? = null) :
    WonderlandError("$type[$id] update nothing, the update command cannot be empty", cause)

  data class InvalidCursor(val cursor: String, override val cause: Throwable? = null) :
    WonderlandError("Cursor[$cursor] is not a valid cursor", cause)
}
