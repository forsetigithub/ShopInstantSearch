package com.example.shopinstantsearch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shopinstantsearch.data.ShopInfo


class ShopInfoDiffCallback : DiffUtil.ItemCallback<ShopInfo>() {
    override fun areItemsTheSame(oldItem: ShopInfo, newItem: ShopInfo): Boolean {
       return oldItem.shopId == newItem.shopId
    }

    override fun areContentsTheSame(oldItem: ShopInfo, newItem: ShopInfo): Boolean {
        return oldItem == newItem
    }
}

class ShopInfoAdapter: ListAdapter<ShopInfo,ShopInfoAdapter.ViewHolder>(ShopInfoDiffCallback()) {

    var data = listOf<ShopInfo>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopInfoAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item_shop_info,parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.arrivalCode.text = item.shopCode
        holder.address1.text = item.address1
        holder.address2.text = item.address2
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val arrivalCode: TextView = itemView.findViewById(R.id.arrival_code)
        val address1: TextView = itemView.findViewById(R.id.address1)
        val address2: TextView = itemView.findViewById(R.id.address2)
    }

}