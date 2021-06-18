package com.example.mock_shop.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.mock_shop.database.Product
import com.example.mock_shop.database.getDatabase
import com.example.mock_shop.domain.Cart
import com.example.mock_shop.repository.Repository
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import java.lang.IllegalArgumentException

class ProductViewModel(application: Application, id: Int): AndroidViewModel(application) {

    private val repository = Repository(getDatabase(application))

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product>
    get() = _product

    private val _response = MutableLiveData<Cart?>()
    val response: LiveData<Cart?>
    get() = _response

    init {
        getProductFromRepository(id)
        _response.value = null
    }

    private fun getProductFromRepository(id: Int) {
        viewModelScope.launch {
           try {
               _product.value =  repository.getProductById(id)
           } catch (e: IOException) {
               Timber.e(e)
           }
        }
    }

    fun addToCart(){
        viewModelScope.launch {
            try {
                _response.value = repository.addToCart(_product.value!!.id)
            } catch (e: IOException) {
                Timber.e(e)
            }
        }
    }

    class Factory(val app: Application, val id: Int): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(ProductViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return ProductViewModel(app, id) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}