package com.fullmvvmsample.baiju.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Singleton class
object MyCoroutines {

    // Higher order fun taking fun as a parameter
    fun main(work: suspend (() -> Unit)) = CoroutineScope(Dispatchers.Main).launch {
        work()
    }


    // To run on background thread (IO) no updating any UI
    fun io(work: suspend (() -> Unit)) = CoroutineScope(Dispatchers.IO).launch {
        work()
    }

}