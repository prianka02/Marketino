package com.ecommapp.marketino.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ecommapp.marketino.R
import kotlinx.coroutines.launch

class ShopFragment : Fragment() {
    private lateinit var categoryrecyclerView: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var viewModel: ShopViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shop, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        ShopViewModel has added
        viewModel = ViewModelProvider(this)[ShopViewModel::class.java]


        categoryrecyclerView = view.findViewById(R.id.rvParent)
        progressBar = view.findViewById(R.id.progressBar)

        categoryrecyclerView.layoutManager = LinearLayoutManager(requireContext())

        categoryAdapter = CategoryAdapter(emptyList()) { item ->

            Log.d("Clicked", item.toString())
//            navigateToDetails(item)
        }
        categoryrecyclerView.adapter = categoryAdapter
//        Handle the Loading
        handleLoading()

//        Get Data from ViewModel
        lifecycleScope.launch {
            viewModel.categoryResponseFlow.collect { response ->
                Log.d("ShopFragment", response.toString())
                val categoryList = response?.data

                if (categoryList != null) {
//                    categoryAdapter = CategoryAdapter(categoryList)
                    categoryAdapter.addNewCategory(categoryList)

                }
            }
        }
    }

    private fun handleLoading() {
        lifecycleScope.launch {
            viewModel.isLoading.collect { isLoading ->
                if (isLoading) {
                    progressBar.visibility = View.VISIBLE
                } else {
                    progressBar.visibility = View.GONE
                }
            }
        }
    }
}