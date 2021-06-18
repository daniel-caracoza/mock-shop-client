package com.example.mock_shop.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.example.mock_shop.database.getDatabase
import com.example.mock_shop.domain.Customer
import com.example.mock_shop.repository.Repository
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.IOException
import java.lang.IllegalArgumentException

class CheckoutViewModel(application: Application): AndroidViewModel(application) {
    private val repository = Repository(getDatabase(application))

    private val _response = MutableLiveData<ResponseBody?>()
    val response: LiveData<ResponseBody?>
    get() = _response

    private val _isFormComplete = MutableLiveData<Boolean>()
    val isFormComplete: LiveData<Boolean>
    get() = _isFormComplete

    init {
        _isFormComplete.value = false
        _response.value = null
    }


    fun completeCheckout() {
        viewModelScope.launch {
            val customer = Customer(
                firstName,
                lastName,
                email,
                address,
                city,
                state,
                zip
            )
            try {
                _response.value = repository.completeCheckout(customer)
            } catch (error: IOException){
                println(error.message)
            }
        }
    }

    fun isFormComplete() {
        _isFormComplete.value =
            (firstName.isNotEmpty()
                && lastName.isNotEmpty()
                && email.isNotEmpty()
                && address.isNotEmpty()
                && city.isNotEmpty()
                && state.isNotEmpty()
                && zip.isNotEmpty())
    }

    var firstName: String = ""
    var lastName: String = ""
    var email: String = ""
    var address: String = ""
    var city: String = ""
    var state: String = ""
    var zip: String = ""

    class Factory(val app: Application): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(CheckoutViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return CheckoutViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}