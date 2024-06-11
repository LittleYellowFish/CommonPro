package com.example.lib_base.mvvm

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.withStateAtLeast
import com.example.lib_base.mvvm.bean.MBResult
import com.example.lib_base.mvvm.exception.ExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * @auther leyu
 * @date   2021/1/14 18:02
 * @desc
 */
/**
 * Created by luyao
 * on 2020/3/30 16:19
 */
inline fun <T : Any> MBResult<T>.checkResult(
    crossinline onSuccess: (T) -> Unit,
    crossinline onError: (String?) -> Unit
) {
    if (this is MBResult.Success) {
        onSuccess(data)
    } else if (this is MBResult.Error) {
        onError(ExceptionHandler.handleException(exception))
    }
}

inline fun <T : Any> MBResult<T>.checkSuccess(success: (T) -> Unit) {
    if (this is MBResult.Success) {
        success(data)
    }
}

inline fun <T : Any> Flow<T>.collectWithLifecycle(
    lifecycleOwner: LifecycleOwner,
    crossinline block: suspend CoroutineScope.(data: T) -> Unit
) {
    lifecycleOwner.lifecycleScope.launch {
        this@collectWithLifecycle.collect {
            block(it)
        }
    }
}


inline fun Fragment.launchAndRepeatWithLifecycle(
    activeState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: () -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.withStateAtLeast(activeState) {
            block()
        }
    }
}