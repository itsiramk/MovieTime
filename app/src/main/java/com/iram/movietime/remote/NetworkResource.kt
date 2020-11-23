package com.iram.movietime.remote

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.iram.movietime.utils.Resource
import retrofit2.Response

abstract class NetworkResource<RequestType> @MainThread constructor() {

    private val result = MediatorLiveData<Resource<RequestType>>()

    init {
        fetchFromNetwork()
    }

    private fun fetchFromNetwork() {
        val apiResponse = fetchService()
        // Make the network call
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)

            // Dispatch the result
            response?.apply {
                when (response.isSuccessful) {
                    true -> {
                        AppExecutors.mainThread().execute {
                          //  setValue(Resource.success(response.body))
                        }
                    }
                    false -> {
                        AppExecutors.mainThread().execute {
                            response.message()?.let {
                                setValue(Resource.error(it, null))
                            }
                        }
                    }
                }
            }
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<RequestType?>) {

       // if (result.value != newValue) result.value = newValue.toString()
    }

    fun asLiveData(): LiveData<Resource<RequestType>> {
        return result
    }

    @MainThread//createCall
    protected abstract fun fetchService(): LiveData<Response<RequestType>>
}