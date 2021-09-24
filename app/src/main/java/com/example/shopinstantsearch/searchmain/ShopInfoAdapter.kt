package com.example.shopinstantsearch.searchmain

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shopinstantsearch.R
import com.example.shopinstantsearch.data.ShopInfo


class ShopListDiffCallback : DiffUtil.ItemCallback<ShopInfo>() {
    override fun areItemsTheSame(oldItem: ShopInfo, newItem: ShopInfo): Boolean {
       return oldItem.shopId == newItem.shopId
    }

    override fun areContentsTheSame(oldItem: ShopInfo, newItem: ShopInfo): Boolean {
        return oldItem == newItem
    }
}

class ShopListAdapter: ListAdapter<ShopInfo, ShopListAdapter.ViewHolder>(ShopListDiffCallback()) {

    var data = listOf<ShopInfo>()
//        set(value) {
//            field = value
//            notifyDataSetChanged()
//        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item_shop_info,parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        val sb = StringBuilder()

        holder.arrivalCode.text = item.shopCode

        sb.append(item.address1)
        sb.append("　")
        sb.append(item.address2)

        holder.address1.text =  sb.toString()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val arrivalCode: TextView = itemView.findViewById(R.id.arrival_code)
        val address1: TextView = itemView.findViewById(R.id.address1)
    }

}

@BindingAdapter("shops")
fun hideProgressBar(view: View, shops: Any?) {
    view.visibility = if (shops != null) View.GONE else View.VISIBLE

}