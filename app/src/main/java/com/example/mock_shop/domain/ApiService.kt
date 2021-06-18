package com.example.mock_shop.domain

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import com.example.mock_shop.database.Product
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import java.net.CookieManager

private const val URL = "https://spiritstormbeauty.shop/api/v1/"
private val cookieJar = JavaNetCookieJar(CookieManager())

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(URL)
    .client(OkHttpClient().newBuilder().cookieJar(cookieJar).build())
    .build()

interface ApiService {

    @GET("products")
    suspend fun getProducts(): List<Product>

    @GET("cart")
    suspend fun getCart(): Cart

    @PUT("cart/add")
    suspend fun addToCart(@Body product_id: RequestBody): Cart

    @PUT("cart/remove/{id}")
    suspend fun deleteFromCart(@Path("id") id: Int): Cart

    @PUT("cart/subtract/{id}")
    suspend fun subtractFromCart(@Path("id") id: Int): Cart

    @POST("checkout")
    suspend fun checkout(@Body customer: Customer): ResponseBody

    object Api {
        val retrofitService: ApiService by lazy {
            retrofit.create(ApiService::class.java)
        }
    }
}
