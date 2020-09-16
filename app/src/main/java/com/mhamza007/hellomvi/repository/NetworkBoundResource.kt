package com.mhamza007.hellomvi.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.mhamza007.hellomvi.util.*
import com.mhamza007.hellomvi.util.Constants.Companion.TESTING_NETWORK_DELAY
import kotlinx.coroutines.*

abstract class NetworkBoundResource<ResponseObject, ViewStateType> {

    protected val result = MediatorLiveData<DataState<ViewStateType>>()

    init {
        result.value = DataState.loading(true)

        GlobalScope.launch(Dispatchers.IO) {
            delay(TESTING_NETWORK_DELAY) // just for testing

            withContext(Dispatchers.Main) {
                val apiResponse = createCall()
                result.addSource(apiResponse) {
                    result.removeSource(apiResponse)
                    handleNetworkCall(it)
                }
            }
        }
    }

    private fun handleNetworkCall(genericApiResponse: GenericApiResponse<ResponseObject>) {
        when (genericApiResponse) {
            is ApiSuccessResponse -> {
                handleApiSuccessResponse(genericApiResponse)
            }
            is ApiErrorResponse -> {
                println("DEBUG: NetworkBoundResource: ${genericApiResponse.errorMessage}")
                onReturnError(genericApiResponse.errorMessage)
            }
            is ApiEmptyResponse -> {
                println("DEBUG: NetworkBoundResource: HTTP 204. Returned NOTHING")
                onReturnError("HTTP 204. Returned NOTHING")
            }
        }
    }

    abstract fun handleApiSuccessResponse(response: ApiSuccessResponse<ResponseObject>)

    private fun onReturnError(message: String) {
        result.value = DataState.error(message)
    }

    abstract fun createCall(): LiveData<GenericApiResponse<ResponseObject>>

    fun asLiveData() = result as LiveData<DataState<ViewStateType>>
}