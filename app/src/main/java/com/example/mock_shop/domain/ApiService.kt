package com.example.mock_shop.domain

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import com.example.mock_shop.database.Product

private const val URL = "https://spiritstormbeauty.shop"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(URL)
    .build()

interface ApiService {

    @GET("/products")
    suspend fun getProducts(): List<Product>

    @GET("/cart")
    suspend fun getCart(): Cart

    @PUT("/cart/add")
    suspend fun addToCart(@Body id: RequestBody): Cart

    @PUT("/cart/remove/{id}")
    suspend fun removeFromCart(@Path("id") id: Int): Cart

    @POST("/checkout")
    suspend fun checkout(@Body customer: Customer): ResponseBody

    object Api {
        val retrofitService: ApiService by lazy {
            retrofit.create(ApiService::class.java)
        }
    }
}
