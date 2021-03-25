package com.ukonnra.wonderland.infrastructure.core.viewinterceptor

import com.ukonnra.wonderland.infrastructure.core.Filter
import com.ukonnra.wonderland.infrastructure.core.Identifier

interface ViewInterceptor<ID : Identifier<ID>> {
  suspend fun getAll(ids: List<ID>, filter: Filter): List<ID>
}
