package com.ecommapp.marketino.ui.home

import ProductAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ecommapp.marketino.R
import com.ecommapp.marketino.data.products.Product
import com.ecommapp.marketino.data.products.ProductResponse
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var viewModel: HomeViewModel

    private lateinit var countText: TextView
    private lateinit var incrementBtn: Button
    private lateinit var decrementBtn: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        recyclerView = view.findViewById(R.id.recyclerView)
        progressBar = view.findViewById(R.id.progressBar)

        countText = view.findViewById(R.id.countView)
        incrementBtn = view.findViewById(R.id.increment)
        decrementBtn = view.findViewById(R.id.decrement)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.addItemDecoration(ItemSpacingDecoration(horizontal = 2, vertical = 2))
        recyclerView.setPadding(8, 0, 0, 0)



        viewModel.count.observe(viewLifecycleOwner) { data ->
            countText.text = data.toString()
        }


        incrementBtn.setOnClickListener {
            viewModel.onIncrement()
        }


        decrementBtn.setOnClickListener {
            viewModel.onDecrement()

        }


        handleLoading()

        viewModel.liveResponse.observe(viewLifecycleOwner) { response ->
            val productList = response?.data?.data
            adapter = ProductAdapter(productList) { item ->
                navigateToDetails(item)
            }
            recyclerView.adapter = adapter
        }


//        lifecycleScope.launch {
//            viewModel.productResponseFlow.collect { response ->
//                Log.d("HomeFragment", response.toString())
//                val productList = response?.data?.data
//                if (productList != null) {
//                  adapter.updateList(productList)
//                }
//            }
//        }


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
                    progressBar.visibility = View.VISIBLE
                } else {
                    progressBar.visibility = View.GONE
                }
            }
        }
    }

}