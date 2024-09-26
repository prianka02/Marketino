
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ecommapp.marketino.R
import com.ecommapp.marketino.data.products.Product
import com.ecommapp.marketino.databinding.SingleRowProductBinding
import java.util.Locale

class ProductAdapter(
    private var productList: List<Product>,
    private val onItemClick: (Product) -> Unit
):
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    class ViewHolder(val binding: SingleRowProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = SingleRowProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productList[position]
        holder.itemView.setOnClickListener { onItemClick(product) }
        holder.binding.apply {
            textProductTitle.text = product.name?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
            textOldPrice.text = "৳${product.price} TK"
            textProductQuantity.text = "Quantity: ${product.quantity.toString()}"
            textDiscount.text = "Save: ৳${product.discount}"
            imageProduct.load(product.main_image){
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