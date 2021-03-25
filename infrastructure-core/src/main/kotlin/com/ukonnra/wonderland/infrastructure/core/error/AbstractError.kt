package com.ukonnra.wonderland.infrastructure.core.error

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.ukonnra.wonderland.infrastructure.core.HttpStatusDeserializer
import com.ukonnra.wonderland.infrastructure.core.HttpStatusSerializer
import org.springframework.http.HttpStatus

@JsonIgnoreProperties("cause", "stackTrace", "suppressed", "localizedMessage")
abstract class AbstractError(override val message: String, override val cause: Throwable?) : RuntimeException
(message, cause) {
  @JsonSerialize(using = HttpStatusSerializer::class)
  @JsonDeserialize(using = HttpStatusDeserializer::class)
  open val statusCode: HttpStatus = HttpStatus.BAD_REQUEST
}
