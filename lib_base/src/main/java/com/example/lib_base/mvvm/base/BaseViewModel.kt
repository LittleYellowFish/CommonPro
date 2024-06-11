package com.example.lib_base.mvvm.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lib_base.mvvm.bean.HttpResult
import com.example.lib_base.mvvm.bean.MBResult
import com.example.lib_base.mvvm.exception.ExceptionHandler
import com.example.lib_base.mvvm.util.LogUtils
import com.example.lib_base.mvvm.checkResult
import kotlinx.coroutines.*

/**
 * Created by luyao
 * on 2019/5/31 16:06
 */
open class BaseViewModel : ViewModel() {


    open class UiState<T>(
        val success: Boolean = false,
        val isLoading: Boolean = false,
        val isRefresh: Boolean = false,
        val isSuccess: T? = null,
        val isError: String? = null
    )


    open class BaseUiModel<T>(
        var isSuccess: Boolean = false,
        var showLoading: Boolean = false,
        var showError: String? = null,
        var showSuccess: T? = null,
        var showEnd: Boolean = false, // 加载更多
        var isRefresh: Boolean = false // 刷新

    )

    val mException: MutableLiveData<Throwable> = MutableLiveData()


    fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch { block() }

    }

    suspend fun <T> launchOnIO(block: suspend CoroutineScope.() -> T) {
        withContext(Dispatchers.IO) {
            block
        }
    }

    class ResultBuilder<T> {
        internal lateinit var api: (suspend () -> HttpResult<T>)

        internal var onSuccess: ((T) -> Unit)? = null
        internal var onError: ((String?) -> Unit)? = null

        fun api(api: (suspend () -> HttpResult<T>)) {
            this.api = api
        }

        fun onSuccess(onSuccess: ((T) -> Unit)?) {
            this.onSuccess = onSuccess
        }

        fun onError(onError: ((String?) -> Unit)?) {
            this.onError = onError
        }
    }

    fun <T : Any> runTaskDSL(
        result: ResultBuilder<T>.() -> Unit
    ) {
        val requestBuilder = ResultBuilder<T>()
        result(requestBuilder)

        launchOnUI {
            runTaskIO {
                requestBuilder.api()
            }.checkResult(onSuccess = {
                requestBuilder.onSuccess?.let { it1 -> it1(it) }
            }, onError = {
                requestBuilder.onError?.let { it1 -> it1(it) }
            })
        }
    }

    fun <T : Any> runTask(
        api: suspend () -> HttpResult<T>,
        onSuccess: ((T) -> Unit)? = null,
        onError: ((String?) -> Unit)? = null
    ) {
        launchOnUI {
            runTaskIO {
                api()
            }.checkResult(onSuccess = {
                onSuccess?.invoke(it)
            }, onError = {
                onError?.invoke(it)
            })
        }
    }

    suspend fun <T : Any> runTaskIO(
        successBlock: (suspend CoroutineScope.() -> Unit)? = null,
        errorBlock: (suspend CoroutineScope.() -> Unit)? = null,
        block: suspend () -> HttpResult<T>
    ): MBResult<T> {
        return withContext(Dispatchers.IO) {
            safeApiCall(call = {
                executeResponse(
                    block(), successBlock, errorBlock
                )
            })
        }

    }

    suspend fun <T : Any> apiCall(call: suspend () -> HttpResult<T>): HttpResult<T> {
        return call.invoke()
    }

    suspend fun <T : Any> safeApiCall(
        call: suspend () -> MBResult<T>,
        errorMessage: String = "未知错误"
    ): MBResult<T> {
        return try {
            call()
        } catch (e: Exception) {
            // An exception was thrown when calling the API so we're converting this to an IOException
            //非业务逻辑上的错误
            LogUtils.e(BaseViewModel::class.java.simpleName, e.toString())
            val commonException = ExceptionHandler.convertCommonException(e)
            MBResult.Error(commonException)
        }
    }

    suspend fun <T : Any> executeResponse(
        response: HttpResult<T>, successBlock: (suspend CoroutineScope.() -> Unit)? = null,
        errorBlock: (suspend CoroutineScope.() -> Unit)? = null
    ): MBResult<T> {
        return coroutineScope {
            if (response.success()) {
                successBlock?.let { it() }
                MBResult.Success(response.data)
            } else {
                //业务逻辑上的错误
                Log.d("BaseRepository", "executeResponse error:" + response.message)
                errorBlock?.let { it() }
                MBResult.Error(IllegalStateException(response.message))
            }
        }
    }
}