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

class SubCategoryAdapter(
    private var subcategoryProd: List<SubCategory?>?,
    private val onItemClick: (Product) -> Unit
):
    RecyclerView.Adapter<SubCategoryAdapter.ChildViewHolder>() {

    class ChildViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemName: TextView = view.findViewById(R.id.sub_category_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sub_category_item, parent, false)
        return ChildViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        val product = subcategoryProd?.get(position)
        holder.itemName.text = product?.name
    }

    override fun getItemCount(): Int {
        return subcategoryProd?.size ?: 0
    }

    fun addNewProduct(response: List<SubCategory>) {
        subcategoryProd = response
        notifyDataSetChanged()
    }
}