package com.example.mock_shop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.mock_shop.databinding.FragmentProductBinding
import com.example.mock_shop.viewmodels.ProductViewModel

class ProductFragment : Fragment() {
    private val args: ProductFragmentArgs by navArgs()

    private val productViewModel: ProductViewModel by lazy {
        val activity = requireNotNull(this.activity){
            "You can only access this class after OnActivityCreated()"
        }
        ViewModelProvider(this, ProductViewModel.Factory(activity.application, args.id))
            .get(ProductViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentProductBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false)
        binding.addToCart.setOnClickListener { addToCart() }
        productViewModel.product.observe(viewLifecycleOwner, {
            it?.let {
                binding.product = it
            }
        })
        return binding.root
    }

    private fun addToCart(){
        productViewModel.addToCart()
    }

}