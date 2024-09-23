
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ecommapp.marketino.R
import com.ecommapp.marketino.data.products.Product
import java.util.Locale

class ProductAdapter(
    private var productList: List<Product>,
    private val onItemClick: (Product) -> Unit
):
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val title: TextView = itemView.findViewById(R.id.textProductTitle)
        val price: TextView = itemView.findViewById(R.id.textOldPrice)
        val quantity: TextView = itemView.findViewById(R.id.textProductQuantity)
        val main_image: ImageView = itemView.findViewById(R.id.imageProduct)
        val discount: TextView = itemView.findViewById(R.id.textDiscount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_row_product, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productList[position]
        holder.itemView.setOnClickListener { onItemClick(product) }
        holder.apply {
            title.text = product.name?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
            price.text = "৳${product.price} TK"
            quantity.text = "Quantity: ${product.quantity.toString()}"
            discount.text = "Save: ৳${product.discount}"
            main_image.load(product.main_image){
                crossfade(true)
                placeholder(R.drawable.placeholder_transparent)  // Default image while loading
                error(R.drawable.errorimage)
            }
        }

    }

    fun updateList(newList: List<Product>) {
        productList = newList
        notifyDataSetChanged()
    }
}