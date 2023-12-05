package com.example.projectsample.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectsample.model.Product
import com.example.projectsample.ProductDetailActivity
import com.example.projectsample.R
import com.example.projectsample.databinding.ProductViewBinding

class CategorizedProductAdapter(
    private val products: ArrayList<Product>,
    val context: Context): RecyclerView.Adapter<CategorizedProductAdapter.PlaceHolder>() {

    interface Listener {
        fun categoryButtonClicked(
            products: ArrayList<Product>,
            holder: PlaceHolder,
            position: Int
        )//service : Service de alabilir.
    }

    class PlaceHolder(val binding: ProductViewBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceHolder {
        val binding =
            ProductViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaceHolder(binding)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: PlaceHolder, position: Int) {
        holder.binding.product = products[position]
        Glide.with(context)
            .load(products[position].thumbnail)
            .override(300,300)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.binding.imageOfProduct)
        holder.binding.textViewProductName.text =  products[position].title
        ("$"+products[position].price.toString()).also { holder.binding.textViewProductPrice.text = it }
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("product",products[position])
            context.startActivity(intent)
        }
        holder.binding.buttonAddToCart.setOnClickListener {
        }
    }

}