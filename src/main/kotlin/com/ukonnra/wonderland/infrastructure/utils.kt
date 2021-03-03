package com.ukonnra.wonderland.infrastructure

import com.google.common.util.concurrent.FutureCallback
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import reactor.core.publisher.Mono
import java.util.concurrent.Executor

inline fun <reified T> ListenableFuture<T>.toMono(executor: Executor) = Mono.create<T> {
  Futures.addCallback(this, object : FutureCallback<T> {
    override fun onSuccess(result: T?) {
      it.success(result)
    }

    override fun onFailure(t: Throwable) {
      it.error(t)
    }
  }, executor)
}
