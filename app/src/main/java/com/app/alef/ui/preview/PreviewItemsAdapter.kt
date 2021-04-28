package com.app.alef.ui.preview

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.app.alef.R
import com.app.alef.data.model.ItemsResponse
import com.squareup.picasso.Picasso


class PreviewItemsAdapter (private val picasso: Picasso, private val context: Context, private val spanCount: Int):RecyclerView.Adapter<PreviewItemsAdapter.ItemViewHolder>(){

    var dataSource:ItemsResponse = ItemsResponse(arrayListOf())
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val width = parent.measuredWidth / spanCount
        val view = inflater.inflate(R.layout.item_view, parent, false)
        view.layoutParams.width = width
        view.layoutParams.height = width
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
            imageViewItem.setOnClickListener {
                imageViewItem.findNavController().navigate(PreviewFragmentDirections.actionPreviewFragmentToDetailItemFragment(uri.toString()))
            }

            picasso.load(uri)
                .fit()
                .centerCrop()
                .into(imageViewItem)
        }
    }

    companion object {
        private const val TAG = "PreviewItemsAdapter"
    }
}