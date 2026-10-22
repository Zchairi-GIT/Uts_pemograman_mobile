package com.example.uts.retrofit

import com.example.uts.response.ProductResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("products")
    fun getListProducts(@Query("limit") limit: Int): Call<ProductResponse>
}