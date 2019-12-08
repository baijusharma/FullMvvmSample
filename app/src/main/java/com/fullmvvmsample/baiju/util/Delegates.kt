package com.fullmvvmsample.baiju.util

import kotlinx.coroutines.*

//Custom lazy Deferred means Job which will return result
fun<T> lazyDeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>>{
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}
