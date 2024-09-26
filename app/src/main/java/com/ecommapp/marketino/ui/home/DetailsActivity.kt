package com.ecommapp.marketino.ui.home

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.ecommapp.marketino.R
import com.ecommapp.marketino.data.products.Product
import com.ecommapp.marketino.databinding.ActivityDetailsBinding
import java.util.Locale

class DetailsActivity : AppCompatActivity() {
//    private lateinit var title: TextView
//    private lateinit var oldPrice: TextView
//    private lateinit var quantity: TextView
//    private lateinit var main_image: ImageView
//    private lateinit var discount: TextView
//    private lateinit var description: TextView
//    private lateinit var category: TextView
//    private lateinit var brand: TextView
//    private lateinit var shippingCost: TextView
    private lateinit var detailsViewBinding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        detailsViewBinding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(detailsViewBinding.root)

//        get the Product details from home items
        val product = intent.getParcelableExtra<Product>("PRODUCT")

//        Set the data to the views
//        Capitalize the title name
        detailsViewBinding.productTitle.text = product?.name?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
        detailsViewBinding.oldPrice.text = "৳${product?.price} TK"
        detailsViewBinding.productQuantity.text = "Quantity: ${product?.quantity.toString()}"
        detailsViewBinding.textDiscount.text = "Save: ৳${product?.discount}"
        detailsViewBinding.prodDescription.text = product?.description
        detailsViewBinding.categoryName.text = product?.category?.name
        detailsViewBinding.brandName.text = product?.brand?.name
        detailsViewBinding.shippingCost.text = "৳${product?.shipping_cost}"


        detailsViewBinding.imageProduct.load(product?.main_image){
            crossfade(true)
            placeholder(R.drawable.placeholder_transparent)  // Default image while loading
            error(R.drawable.errorimage)
        }

    }
}