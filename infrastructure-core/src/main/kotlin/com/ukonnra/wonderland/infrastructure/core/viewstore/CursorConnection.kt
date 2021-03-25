package com.ukonnra.wonderland.infrastructure.core.viewstore

import com.ukonnra.wonderland.infrastructure.core.Aggregate
import com.ukonnra.wonderland.infrastructure.core.Identifier

data class CursorConnection<A : Aggregate<ID>, ID : Identifier<ID>>(
  val data: List<CursorEdge<A, ID>>,
  val hasPreviousPage: Boolean,
  val hasNextPage: Boolean
)
