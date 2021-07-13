package com.vsimpleton.template.utils

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach

/**
 * 使用协程 + Flow实现倒计时
 */
fun countDownCoroutines(
    total: Int, onTick: (Int) -> Unit, onFinish: () -> Unit,
    scope: CoroutineScope
): Job {
    return flow {
        for (i in total downTo 0) {
            emit(i)
            delay(1000)
        }
    }
        .flowOn(Dispatchers.IO)
        .onCompletion { onFinish.invoke() }
        .onEach { onTick.invoke(it) }
        .flowOn(Dispatchers.Main)
        .launchIn(scope)
}

