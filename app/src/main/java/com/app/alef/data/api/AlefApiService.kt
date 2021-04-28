package com.app.alef.data.api

import io.reactivex.Single
import retrofit2.http.GET


interface AlefApiService {
    @GET("task-m-001/list.php")
    fun getItems(): Single<List<String>>
}