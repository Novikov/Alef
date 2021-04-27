package com.app.alef.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.alef.data.api.AlefApiService
import com.app.alef.data.model.ItemsResponseConverter
import com.app.alef.data.model.ItemsResponse
import com.app.alef.ui.preview.di.PreviewScope
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@PreviewScope
class ItemsDataSource @Inject constructor(
    private val apiService: AlefApiService,
    private val compositeDisposable: CompositeDisposable,
    private val _networkState: MutableLiveData<NetworkState>,
    private val _downloadedItemsResponse : MutableLiveData <ItemsResponse>,
    private val itemsResponseConverter: ItemsResponseConverter
){
    val networkState: LiveData<NetworkState>
        get() = _networkState

    val downloadedItems : LiveData<ItemsResponse>
    get() = _downloadedItemsResponse

    fun getItems(){
        _networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                apiService.getItems()
                    .observeOn(Schedulers.io())
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        val itemsResponse = itemsResponseConverter.getItemResponse(it)
                        _downloadedItemsResponse.postValue(itemsResponse)
                        _networkState.postValue(NetworkState.LOADED)
                    },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                        })
            )
        }catch (e: Exception) {
            Log.e(TAG, e.message.toString())
        }

    }

    companion object {
        private const val TAG = "ImageItemsDataSource"
    }
}