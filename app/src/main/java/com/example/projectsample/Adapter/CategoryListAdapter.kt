package com.example.projectsample.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.projectsample.R
import com.example.projectsample.databinding.CategoryListViewBinding

class CategoryListAdapter(private val categories: ArrayList<String>,
                          val context: Context,
                          private val listener: Listener) : RecyclerView.Adapter<CategoryListAdapter.PlaceHolder>() {
    private var selectedItemPosition: Int = 0
    class PlaceHolder(val binding: CategoryListViewBinding) : RecyclerView.ViewHolder(binding.root) {}

    interface Listener {
        fun onItemClick(
            position: Int,
            holder: PlaceHolder,
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceHolder {
        val binding =
            CategoryListViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaceHolder(binding)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: PlaceHolder, @SuppressLint("RecyclerView") position: Int) {
        val item = holder.binding
        with(holder) {
            with(categories[position]) {
                binding.buttonEachCategory.text = this
            }
        }
        holder.binding.buttonEachCategory.setOnClickListener {
            // holder.binding.buttonEachCategory.setBackgroundColor(coloredBackground)
            selectedItemPosition = position
            listener.onItemClick(position, holder)
            notifyDataSetChanged()
        }
        if (selectedItemPosition == position) {
            item.buttonEachCategory.setBackgroundColor(Color.parseColor("#ffffff"))
            item.buttonEachCategory.setTextColor(ContextCompat.getColor(context, R.color.black))
        } else {
            item.buttonEachCategory.setBackgroundColor(Color.parseColor("#000000"))
            item.buttonEachCategory.setTextColor(ContextCompat.getColor(context, R.color.white))
        }
    }
}