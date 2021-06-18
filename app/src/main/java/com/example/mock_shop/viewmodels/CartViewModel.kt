package com.example.mock_shop.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.mock_shop.database.getDatabase
import com.example.mock_shop.domain.Cart
import com.example.mock_shop.repository.Repository
import kotlinx.coroutines.launch
import java.io.IOException
import java.lang.IllegalArgumentException

class CartViewModel(application: Application): AndroidViewModel(application) {

    private val repository = Repository(getDatabase(application))

    private val _cart = MutableLiveData<Cart>()
    val cart: LiveData<Cart>
    get() = _cart

    private val _total = MutableLiveData<Double>()
    val total: LiveData<Double>
    get() = _total

    private val _isCheckoutEnabled = MutableLiveData<Boolean>()
    val isCheckoutEnabled: LiveData<Boolean>
    get() = _isCheckoutEnabled

    private val _error = MutableLiveData<IOException?>()
    val error: LiveData<IOException?>
    get() = _error


    init {
        getCartFromRepository()
        _isCheckoutEnabled.value = false
        _error.value = null
    }
    fun toggleCheckoutButton(bool: Boolean){
        _isCheckoutEnabled.value = bool
    }
    fun updateTotal(total: Double){
        _total.value = total
    }

    private fun getCartFromRepository(){
        viewModelScope.launch {
            try {
                _cart.value = repository.getCart()
            } catch (e: IOException) {
                _error.value = e
            }
        }
    }

    fun addToCartFromRepository(id: Int){
        viewModelScope.launch {
            try {
                _cart.value = repository.addToCart(id)
            } catch (e: IOException) {
                _error.value = e
            }
        }
    }

    fun subtractFromCartViaRepository(id: Int){
        viewModelScope.launch {
            try {
                _cart.value = repository.subtractFromCart(id)
            } catch (e: IOException) {
                _error.value = e
            }
        }
    }

    fun deleteFromCartViaRepository(id: Int){
        viewModelScope.launch {
            try {
                _cart.value = repository.deleteFromCart(id)
            } catch (e: IOException) {
                _error.value = e
            }
        }
    }

    class Factory(val app: Application): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(CartViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return CartViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
