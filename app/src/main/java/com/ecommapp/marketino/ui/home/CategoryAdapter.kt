package com.ecommapp.marketino.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ecommapp.marketino.R
import com.ecommapp.marketino.data.category.Data
import com.ecommapp.marketino.data.products.Product
import com.ecommapp.marketino.databinding.CategoryItemBinding
import java.util.Locale

class CategoryAdapter(
    private var categoryList: List<Data?>?,
    private val onProductClick: (Product) -> Unit

) :RecyclerView.Adapter<CategoryAdapter.ParentViewHolder>() {

    class ParentViewHolder(val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        var view = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ParentViewHolder(view)
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {

        val category = categoryList?.get(position)

        holder.binding.tvCategoryTitle.text = category?.name?.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.ROOT
            ) else it.toString()
        }

        holder.binding.rvChildItems.layoutManager = LinearLayoutManager(holder.itemView.context, RecyclerView.HORIZONTAL,false)
//        holder.subCategoryRecyclerView.layoutManager = GridLayoutManager(holder.itemView.context, 3)
        holder.binding.rvChildItems.adapter = SubCategoryAdapter(category?.sub_categories, onProductClick)

    }

    override fun getItemCount(): Int {
        return categoryList?.size ?: 0
    }

    // Function to update the adapter with new data
    fun addNewCategory(response: List<Data?>?) {
        categoryList = response
        notifyDataSetChanged()
    }
}
