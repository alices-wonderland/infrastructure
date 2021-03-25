package com.ukonnra.wonderland.infrastructure.error

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.ukonnra.wonderland.infrastructure.HttpStatusDeserializer
import com.ukonnra.wonderland.infrastructure.HttpStatusSerializer
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
