package com.app.alef.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.alef.data.api.AlefApiService
import com.app.alef.data.model.ItemsResponse
import com.app.alef.data.model.ItemsResponseConverter
import com.app.alef.ui.preview.di.PreviewScope
import com.app.alef.utils.NO_INTERNET_ERROR_MSG
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

@PreviewScope
class ItemsDataSource @Inject constructor(
    private val apiService: AlefApiService,
    private val compositeDisposable: CompositeDisposable,
    private val _networkState: MutableLiveData<NetworkState>,
    private val _downloadedItemsResponse: MutableLiveData<ItemsResponse>,
    private val itemsResponseConverter: ItemsResponseConverter
) {
    val networkState: LiveData<NetworkState>
        get() = _networkState

    val downloadedItems: LiveData<ItemsResponse>
        get() = _downloadedItemsResponse

    fun getItems() {
        _networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                apiService.getItems()
                    .observeOn(Schedulers.io())
                    .subscribeOn(Schedulers.io())
                    .flatMapObservable { items ->
                        Observable.fromIterable(items)
                            .filter {
                                var code = 0
                                try {
                                    val url = URL(it)
                                    val connection = url.openConnection() as HttpURLConnection
                                    connection.requestMethod = "GET"
                                    connection.connect()
                                    code = connection.responseCode
                                } catch (e: Exception) {
                                    Log.e(TAG, e.message.toString())
                                }
                                code == 200
                            }
                            .observeOn(Schedulers.io())
                            .subscribeOn(Schedulers.io())
                    }
                    .toList()
                    .subscribe({ urlList ->
                        val itemsResponse = itemsResponseConverter.getItemResponse(urlList)
                        _downloadedItemsResponse.postValue(itemsResponse)
                        _networkState.postValue(NetworkState.LOADED)
                    },
                        {
                            when {
                                it.message?.contains(NO_INTERNET_ERROR_MSG)!! -> {
                                    _networkState.postValue(NetworkState.NO_INTERNET)
                                }
                                else -> {
                                    Log.i(TAG, it.message.toString())
                                    _networkState.postValue(NetworkState.ERROR)
                                }
                            }
                        })
            )
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
        }
    }

    companion object {
        private const val TAG = "ImageItemsDataSource"
    }
}