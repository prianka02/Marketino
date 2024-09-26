package com.ecommapp.marketino.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ecommapp.marketino.R
import com.ecommapp.marketino.data.category.SubCategory
import com.ecommapp.marketino.data.products.Product
import com.ecommapp.marketino.databinding.SubCategoryItemBinding

class SubCategoryAdapter(
    private var subcategoryProd: List<SubCategory?>?,
    private val onItemClick: (Product) -> Unit
):
    RecyclerView.Adapter<SubCategoryAdapter.ChildViewHolder>() {

    class ChildViewHolder(val binding: SubCategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
//        var itemName: TextView = view.findViewById(R.id.sub_category_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val view = SubCategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChildViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        val product = subcategoryProd?.get(position)
        holder.binding.subCategoryName.text = product?.name
    }

    override fun getItemCount(): Int {
        return subcategoryProd?.size ?: 0
    }

    fun addNewProduct(response: List<SubCategory>) {
        subcategoryProd = response
        notifyDataSetChanged()
    }
}