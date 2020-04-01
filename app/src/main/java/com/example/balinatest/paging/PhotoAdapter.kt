package com.example.balinatest.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.CircleCropTransformation
import com.example.balinatest.R
import com.example.balinatest.network.data_models.ContentItem
import kotlinx.android.synthetic.main.list_item.view.*

class PhotoAdapter(val callback: (id: Int) -> Unit)
    : PagedListAdapter<ContentItem, PhotoAdapter.MyViewHolder>(DiffUtilCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, callback) }
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.imageView
        val name = itemView.name
        val id = itemView.idItem

        fun bind(item : ContentItem, callback: (id: Int) -> Unit){
            if(item.image != null){
                image.load(item.image?.replace("http", "https")){
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }
            }else{
                image.load(R.drawable.question){
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }
            }
            name.text = item.name
            id.text = "id:" + item.id
            itemView.setOnClickListener {
                callback.invoke(item.id ?: 0)
            }
        }
    }
}

class DiffUtilCallBack : DiffUtil.ItemCallback<ContentItem>() {
    override fun areItemsTheSame(oldItem: ContentItem, newItem: ContentItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ContentItem, newItem: ContentItem): Boolean {
        return oldItem == newItem
    }

}