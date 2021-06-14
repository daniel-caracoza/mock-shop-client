package com.example.mock_shop.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.mock_shop.database.getDatabase
import com.example.mock_shop.repository.Repository
import kotlinx.coroutines.*
import java.io.IOException
import java.lang.IllegalArgumentException

class HomeViewModel(application: Application): AndroidViewModel(application) {

    private val repository = Repository(getDatabase(application))

    val productList = repository.products

    init {
        getProductsFromRepository()
    }

    private fun getProductsFromRepository(){
        viewModelScope.launch {
            try {
                repository.refreshProducts()
            } catch(networkError: IOException){
                print(networkError.message)
            }
        }
    }

    class Factory(val app: Application): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(HomeViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}