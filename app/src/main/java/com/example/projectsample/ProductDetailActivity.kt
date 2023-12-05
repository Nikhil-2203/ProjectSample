package com.example.projectsample

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.projectsample.Adapter.ImageAdapter
import com.example.projectsample.databinding.ActivityProductDetailBinding
import com.example.projectsample.model.Product

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    private var imageList = ArrayList<String>()
    private lateinit var imageAdapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     binding = ActivityProductDetailBinding.inflate(layoutInflater)
     setContentView(binding.root)

        val products = intent.getParcelableExtra<Product>("product")
        if (products != null) {
            products.images?.forEach {
                imageList.add(it)
            }
        }
        if (products != null) {
            if (!imageList.contains(products.thumbnail)) {
                imageList.add(products.thumbnail.toString())
            }
        }
        binding.apply {
            textViewProductDescription.text = products?.description
            ("Stock: " + products?.stock.toString()).also { textViewProductFeatures.text = it }
            ("$" + products?.price.toString()).also { textViewProductPrice.text = it }
            textViewProductName.text = products?.title
            ratingBar.rating = products?.rating!!.toString().toFloat()
            binding.backButton.setOnClickListener {
                finish()
            }
        }
        initializeRecyclerView()
    }
    private fun initializeRecyclerView() {
        val layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding.recyclerViewAllImages.layoutManager = layoutManager
        imageAdapter = ImageAdapter(imageList, this)
        binding.recyclerViewAllImages.adapter = imageAdapter
        PagerSnapHelper().attachToRecyclerView(binding.recyclerViewAllImages)

    }
}