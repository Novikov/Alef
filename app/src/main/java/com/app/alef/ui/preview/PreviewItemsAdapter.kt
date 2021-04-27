package com.app.alef.ui.preview

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.app.alef.R
import com.app.alef.data.model.ItemsResponse
import com.squareup.picasso.Picasso
import javax.inject.Inject

class PreviewItemsAdapter @Inject constructor(private val picasso: Picasso, private val context: Context):RecyclerView.Adapter<PreviewItemsAdapter.ItemViewHolder>(){

    var dataSource:ItemsResponse = ItemsResponse(arrayListOf())
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = inflater.inflate(R.layout.item_view, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(dataSource.itemsList[position].itemUri)
    }

    override fun getItemCount(): Int {
       return dataSource.itemsList.size
    }

    inner class ItemViewHolder (view: View) : RecyclerView.ViewHolder(view){
        val imageViewItem = view.findViewById<ImageView>(R.id.image_view_item)

        fun bind(uri: Uri){
            picasso.load(uri)
                .fit()
                .centerCrop()
                .into(imageViewItem)
        }
    }
}