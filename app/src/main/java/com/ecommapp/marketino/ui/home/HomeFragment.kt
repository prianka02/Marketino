package com.ecommapp.marketino.ui.home

import ProductAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ecommapp.marketino.R
import com.ecommapp.marketino.data.products.Product
import com.ecommapp.marketino.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var adapter: ProductAdapter
    private lateinit var viewModel: HomeViewModel
    private lateinit var homeBinding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeBinding = FragmentHomeBinding.bind(view)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        homeBinding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        homeBinding.recyclerView.addItemDecoration(ItemSpacingDecoration(horizontal = 2, vertical = 2))
        homeBinding.recyclerView.setPadding(8, 0, 0, 0)

//        Handle the Loading
        handleLoading()

        lifecycleScope.launch {
            viewModel.productResponseFlow.collect { response ->
                Log.d("HomeFragment", response.toString())
                val productList = response?.data?.data

                if (productList != null) {
                    adapter = ProductAdapter(productList) { item ->
                        navigateToDetails(item)
                    }
                    homeBinding.recyclerView.adapter = adapter
                }
            }
        }
    }

    private fun navigateToDetails(item: Product) {
        val intent = Intent(requireContext(), DetailsActivity::class.java).apply {
            putExtra("PRODUCT", item)
        }
        startActivity(intent)
    }

    private fun handleLoading() {
        lifecycleScope.launch {
            viewModel.isLoading.collect { isLoading ->
                if (isLoading) {
                    homeBinding.progressBar.visibility = View.VISIBLE
                } else {
                    homeBinding.progressBar.visibility = View.GONE
                }
            }
        }
    }
}