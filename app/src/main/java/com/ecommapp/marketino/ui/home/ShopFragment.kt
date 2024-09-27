package com.ecommapp.marketino.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ecommapp.marketino.R
import com.ecommapp.marketino.databinding.FragmentShopBinding
import com.ecommapp.marketino.databinding.NavViewHeaderBinding
import kotlinx.coroutines.launch

class ShopFragment : Fragment() {
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var viewModel: ShopViewModel
    private lateinit var categoryBinding: FragmentShopBinding
    private lateinit var navViewHeaderBinding: NavViewHeaderBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shop, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        View binding added
        categoryBinding = FragmentShopBinding.bind(view)
        navViewHeaderBinding = NavViewHeaderBinding.bind(categoryBinding.navView.getHeaderView(0))


//        ShopViewModel has added
        viewModel = ViewModelProvider(this)[ShopViewModel::class.java]


        //  Handle Navigation icon Click
        categoryBinding.topAppBar.setOnClickListener {
//            Open's the Navigation Drawer
            categoryBinding.drawerLayout.openDrawer(categoryBinding.navView)
        }

//        Top Bar items Selection
        categoryBinding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.search -> {
                    // Handle edit text press
                    Toast.makeText(requireContext(), "Search Clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.more -> {
                    // Handle more item (inside overflow menu) press
                    Toast.makeText(requireContext(), "More Clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
        navViewHeaderBinding.logoutBtn.setOnClickListener {
            Toast.makeText(requireContext(), "Logout Clicked", Toast.LENGTH_SHORT).show()
        }

//        Add layout manager in the recycler view
        categoryBinding.rvParent.layoutManager = LinearLayoutManager(requireContext())

//        Set Empty adapter
        categoryAdapter = CategoryAdapter(emptyList()) { item ->
            Log.d("Clicked", item.toString())
//            navigateToDetails(item)
        }
        categoryBinding.rvParent.adapter = categoryAdapter

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
                    categoryBinding.progressBar.visibility = View.VISIBLE
                } else {
                    categoryBinding.progressBar.visibility = View.GONE
                }
            }
        }
    }
}