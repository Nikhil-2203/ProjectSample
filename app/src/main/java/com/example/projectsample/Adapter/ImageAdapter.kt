package com.example.projectsample.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.projectsample.R
import com.example.projectsample.databinding.EachImageBinding

class ImageAdapter(private val imageList : ArrayList<String>, val context: Context) : RecyclerView.Adapter<ImageAdapter.PlaceHolder>() {

    class PlaceHolder(val binding : EachImageBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceHolder {
        val binding = EachImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaceHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaceHolder, position: Int) {
        Glide.with(context)
            .load(imageList[position])
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.binding.viewEachImage)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}