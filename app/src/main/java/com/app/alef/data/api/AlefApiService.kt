package com.app.alef.data.api

import com.app.alef.data.model.ImageItemsResponse
import io.reactivex.Single
import retrofit2.http.GET

interface AlefApiService {
    @GET
    fun getMediaPreviews(): Single<ImageItemsResponse>
}