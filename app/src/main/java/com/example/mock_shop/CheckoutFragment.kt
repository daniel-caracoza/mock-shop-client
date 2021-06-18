package com.example.mock_shop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mock_shop.databinding.FragmentCheckoutBinding
import com.example.mock_shop.viewmodels.CheckoutViewModel


class CheckoutFragment : Fragment() {

    private val args:  CheckoutFragmentArgs by navArgs()
    lateinit var viewModel: CheckoutViewModel
    lateinit var binding: FragmentCheckoutBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_checkout, container, false)
        viewModel = ViewModelProvider(this, CheckoutViewModel.Factory(requireActivity().application)).get(CheckoutViewModel::class.java)
        binding.viewModel = viewModel
        binding.total.text = getString(R.string.price, args.subtotal.toString())
        binding.completeCheckout.setOnClickListener {
            viewModel.completeCheckout()
        }
        viewModel.isFormComplete.observe(viewLifecycleOwner, {
            it?.let {
                binding.completeCheckout.isEnabled = it
            }
        })
        viewModel.response.observe(viewLifecycleOwner, {
            it?.let {
                Toast.makeText(requireContext(), it.string(), Toast.LENGTH_LONG).show()
                val directions = CheckoutFragmentDirections.actionCheckoutFragmentToHomeFragment()
                findNavController().navigate(directions)
            }
        })
        doAfterEditTextChange()
        return binding.root
    }

    private fun doAfterEditTextChange(){
        binding.firstNameEdit.editText?.doAfterTextChanged {
            viewModel.firstName = it.toString()
            viewModel.isFormComplete()
        }
        binding.lastNameEdit.editText?.doAfterTextChanged {
            viewModel.lastName = it.toString()
            viewModel.isFormComplete()
        }
        binding.emailEdit.editText?.doAfterTextChanged {
            viewModel.email = it.toString()
            viewModel.isFormComplete()
        }
        binding.addressEdit.editText?.doAfterTextChanged {
            viewModel.address = it.toString()
            viewModel.isFormComplete()
        }
        binding.cityEdit.editText?.doAfterTextChanged {
            viewModel.city = it.toString()
            viewModel.isFormComplete()
        }
        binding.stateEdit.editText?.doAfterTextChanged {
            viewModel.state = it.toString()
            viewModel.isFormComplete()
        }
        binding.zipEdit.editText?.doAfterTextChanged {
            viewModel.zip = it.toString()
            viewModel.isFormComplete()
        }

    }
}