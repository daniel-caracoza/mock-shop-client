package com.example.mock_shop.repository

import androidx.lifecycle.LiveData
import com.example.mock_shop.database.Product
import com.example.mock_shop.database.ProductDatabase
import com.example.mock_shop.domain.ApiService
import com.example.mock_shop.domain.Cart
import com.example.mock_shop.domain.Customer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
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
        return withContext(Dispatchers.IO) {
            val jsonObject = JSONObject()
            val mediaType = ("application/json; charset=utf-8").toMediaType()
            jsonObject.put("product_id", id)
            val body = jsonObject.toString().toRequestBody(mediaType)
            ApiService.Api.retrofitService.addToCart(body)
        }
    }

    suspend fun subtractFromCart(id: Int): Cart {
        return withContext(Dispatchers.IO){
            ApiService.Api.retrofitService.subtractFromCart(id)
        }
    }

    suspend fun deleteFromCart(id: Int): Cart{
        return withContext(Dispatchers.IO){
            ApiService.Api.retrofitService.deleteFromCart(id);
        }
    }

    suspend fun getCart(): Cart {
        return withContext(Dispatchers.IO){
            ApiService.Api.retrofitService.getCart()
        }
    }

    suspend fun completeCheckout(customer: Customer): ResponseBody {
        return withContext(Dispatchers.IO){
            ApiService.Api.retrofitService.checkout(customer)
        }
    }

}