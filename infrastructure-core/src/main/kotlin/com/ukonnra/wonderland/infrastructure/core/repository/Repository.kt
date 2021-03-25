package com.ukonnra.wonderland.infrastructure.core.repository

import com.ukonnra.wonderland.infrastructure.core.Aggregate
import com.ukonnra.wonderland.infrastructure.core.Identifier
import reactor.core.publisher.Mono

interface Repository<A : Aggregate<ID>, ID : Identifier<ID>> {
  fun saveAll(aggregates: List<A>): Mono<Void>
  fun getById(targetId: ID): Mono<A?>
}
