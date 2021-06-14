package com.example.mock_shop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mock_shop.databinding.FragmentHomeBinding
import com.example.mock_shop.database.Product
import com.example.mock_shop.viewmodels.HomeViewModel
import com.example.mock_shop.ui.ProductClickListener
import com.example.mock_shop.ui.ProductsAdapter

class HomeFragment : Fragment(), ProductClickListener {

    private val homeViewModel: HomeViewModel by lazy {
        val activity = requireNotNull(this.activity){
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, HomeViewModel.Factory(activity.application))
            .get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.layoutManager = layoutManager
        val adapter = ProductsAdapter(this)
        binding.recyclerView.adapter = adapter
        homeViewModel.productList.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it)
            }
        })
        return binding.root
    }

    override fun onClick(cardView: View, product: Product) {
        val directions = HomeFragmentDirections.actionHomeFragmentToProductFragment(product.id)
        findNavController().navigate(directions)
    }

}