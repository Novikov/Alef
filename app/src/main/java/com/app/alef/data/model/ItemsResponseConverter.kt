package com.app.alef.data.model

import android.net.Uri
import android.util.Log
import com.app.alef.data.repository.ItemsDataSource
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

class ItemsResponseConverter @Inject constructor(){

    fun getItemResponse(rawData:List<String>): ItemsResponse {
        val itemsResponse = arrayListOf<Item>()
        rawData.forEach {

            val uri = Uri.parse(it)
            val item = Item(uri)
            itemsResponse.add(item)
        }
        return ItemsResponse(itemsResponse)
    }


    companion object {
        private const val TAG = "ItemsResponseConverter"
    }

}