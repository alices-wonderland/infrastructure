package com.ukonnra.wonderland.infrastructure.core.error

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.ukonnra.wonderland.infrastructure.core.HttpStatusDeserializer
import com.ukonnra.wonderland.infrastructure.core.HttpStatusSerializer
import org.springframework.http.HttpStatus

data class ExternalError(
  val code: String,
  val data: Map<String, Any> = emptyMap(),
  override val message: String = "<Unknown External Error>",
  @JsonSerialize(using = HttpStatusSerializer::class)
  @JsonDeserialize(using = HttpStatusDeserializer::class)
  override val statusCode: HttpStatus = HttpStatus.BAD_REQUEST,
  override val cause: Throwable? = null,
) : AbstractError(message, cause)
