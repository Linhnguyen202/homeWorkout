package com.example.homeworkout.ui.main.homeFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homeworkout.R
import com.example.homeworkout.data.remote.model.Category
import com.example.homeworkout.databinding.CategoryItemBinding
 class CategoryAdapter(val onClickItem : (Category) -> Unit)  :  RecyclerView.Adapter<CategoryAdapter.CateViewHolder>() {
    val differ = AsyncListDiffer(this, differCallback)

    inner class CateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = CategoryItemBinding.bind(itemView)
        fun inject(category: Category){
            binding.cateTitle.text = category.title
            Glide.with(itemView).load(category.image_path).into(binding.banner)
            binding.bannerContainer.setOnClickListener {
                onClickItem.invoke(category)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CateViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return CateViewHolder(view)
    }

    override fun onBindViewHolder(holder: CateViewHolder, position: Int) {
        holder.inject(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    companion object {
        private val differCallback = object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem == newItem

            }

            override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem == newItem
            }

        }
    }


}