package com.example.mock_shop

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mock_shop.database.Product
import com.example.mock_shop.databinding.FragmentCartBinding
import com.example.mock_shop.ui.CartProductClickListener
import com.example.mock_shop.ui.CartProductsAdapter
import com.example.mock_shop.viewmodels.CartViewModel


class CartFragment : Fragment(), CartProductClickListener {
    val TAG = "Cart Fragment"

    private lateinit var viewModel: CartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentCartBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false)
        viewModel = ViewModelProvider(this, CartViewModel.Factory(requireActivity().application)).get(CartViewModel::class.java)
        val adapter = CartProductsAdapter(this)
        binding.recyclerView.adapter = adapter
        viewModel.cart.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it.products)
                viewModel.toggleCheckoutButton(it.products.isNotEmpty())
                viewModel.updateTotal(it.total)
            }
        })
        viewModel.total.observe(viewLifecycleOwner, {
            it?.let {
                binding.total.text = getString(R.string.price, it.toString())
            }
        })
        viewModel.isCheckoutEnabled.observe(viewLifecycleOwner, {
            it?.let {
                binding.checkoutButton.isEnabled = it
            }
        })
        binding.checkoutButton.setOnClickListener {
            navigateToCheckout()
        }
        viewModel.error.observe(viewLifecycleOwner, {
            it?.let {
                Log.e(TAG, it.message.toString())
            }
        })
        return binding.root
    }

    override fun onIncrementQuantityClick(product: Product) {
        viewModel.addToCartFromRepository(product.id)
    }

    override fun onDecrementQuantityClick(product: Product) {
        viewModel.subtractFromCartViaRepository(product.id)
    }

    override fun onDeleteClick(product: Product) {
        viewModel.deleteFromCartViaRepository(product.id)
    }

    private fun navigateToCheckout(){
        val directions = CartFragmentDirections.actionCartFragmentToCheckoutFragment(viewModel.total.value!!.toFloat())
        findNavController().navigate(directions)
    }

}