package com.app.alef.ui.preview

import androidx.lifecycle.LiveData
import com.app.alef.data.model.ItemsResponse
import com.app.alef.data.repository.ItemsDataSource
import com.app.alef.data.repository.NetworkState
import com.app.alef.ui.preview.di.PreviewScope
import javax.inject.Inject

@PreviewScope
class PreviewRepository @Inject constructor(private val itemsDataSource:ItemsDataSource){
    fun getItems(): LiveData<ItemsResponse> {
        itemsDataSource.getItems()
        return itemsDataSource.downloadedItems
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return itemsDataSource.networkState
    }
}