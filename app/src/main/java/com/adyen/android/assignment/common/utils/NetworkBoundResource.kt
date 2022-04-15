package com.adyen.android.assignment.common.utils

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = {true},
    crossinline onFetchSuccess: () -> Unit = { },
    crossinline onFetchFailed: (Throwable) -> Unit = { }
) = channelFlow{
    /** GET DATA FROM DATABASE */
    val data = query().first()

    /** CHECK IF DATA SHOULD BE FETCHED FROM INTERNET */
    if(shouldFetch(data)) {
        /** GET DATABASE DATA(IN THE BACKGROUND) WHILE PAGE IS LOADING/DATA IS FETCHED FROM INTERNET */
        /** COROUTINE SCOPE USED HERE IS INHERITED FORM THE ONE USED IN THE VIEWMODEL THAT THIS FUNCTION WILL BE CALLED(VIEWMODELSCOPE) */
        val loading = launch {
            query().collect { send(Resource.Loading(it)) }
        }

        try {
            /** GET DATA FROM INTERNET */
            saveFetchResult(fetch())
            onFetchSuccess()
            loading.cancel()
            query().collect { send(Resource.Success(it)) }
        } catch (t: Throwable) {
            /** HANDLE SCENARIO WHEN FETCHING INTERNET DATA FAILS */
            onFetchFailed(t)
            loading.cancel()
            query().collect { send(Resource.Error(t, it)) }
        }
    } else {
        /** USE DATABASE DATA IF DATA IS UP TO DATE */
        query().collect { send(Resource.Success(it)) }
    }
}