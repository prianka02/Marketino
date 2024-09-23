package com.ecommapp.marketino.ui.home

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil.load
import com.ecommapp.marketino.R
import com.ecommapp.marketino.data.products.Product
import java.util.Locale

class DetailsActivity : AppCompatActivity() {
    private lateinit var title: TextView
    private lateinit var oldPrice: TextView
    private lateinit var quantity: TextView
    private lateinit var main_image: ImageView
    private lateinit var discount: TextView
    private lateinit var description: TextView
    private lateinit var category: TextView
    private lateinit var brand: TextView
    private lateinit var shippingCost: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_details)

        title = findViewById(R.id.product_title)
        oldPrice = findViewById(R.id.old_price)
        quantity = findViewById(R.id.product_quantity)
        main_image = findViewById(R.id.image_product)
        discount = findViewById(R.id.text_discount)
        description = findViewById(R.id.prod_description)
        category = findViewById(R.id.category_name)
        brand = findViewById(R.id.brand_name)
        shippingCost = findViewById(R.id.shipping_cost)

//        get the Product details from home items
        val product = intent.getParcelableExtra<Product>("PRODUCT")

//        Set the data to the views
//        Capitalize the title name
        title.text = product?.name?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
        oldPrice.text = "৳${product?.price} TK"
        quantity.text = "Quantity: ${product?.quantity.toString()}"
        discount.text = "Save: ৳${product?.discount}"
        description.text = product?.description
        category.text = product?.category?.name
        brand.text = product?.brand?.name
        shippingCost.text = "৳${product?.shipping_cost}"


        main_image.load(product?.main_image){
            crossfade(true)
            placeholder(R.drawable.placeholder_transparent)  // Default image while loading
            error(R.drawable.errorimage)
        }


    }
}