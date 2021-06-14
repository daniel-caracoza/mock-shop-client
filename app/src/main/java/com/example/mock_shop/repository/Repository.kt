package com.example.mock_shop.repository

import androidx.lifecycle.LiveData
import com.example.mock_shop.database.Product
import com.example.mock_shop.database.ProductDatabase
import com.example.mock_shop.domain.ApiService
import com.example.mock_shop.domain.Cart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject

class Repository(private val database: ProductDatabase) {

    val products: LiveData<List<Product>> = database.productDao.getProducts()

    suspend fun refreshProducts(){
        withContext(Dispatchers.IO){
            val products = ApiService.Api.retrofitService.getProducts()
            database.productDao.insertAll(products)
        }
    }

    suspend fun getProductById(id: Int): Product {
        return withContext(Dispatchers.IO){
            database.productDao.getProductById(id)
        }
    }

    suspend fun addToCart(id: Int): Cart {
        return withContext(Dispatchers.IO){
            val jsonObject = JSONObject()
            val mediaType = MediaType.get("application/json; charset=utf-8")
            jsonObject.put("id", id)
            val body = RequestBody.create(mediaType, jsonObject.toString())
            ApiService.Api.retrofitService.addToCart(body)
        }
    }

}