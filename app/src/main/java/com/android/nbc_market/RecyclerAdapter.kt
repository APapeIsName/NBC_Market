package com.android.nbc_market

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.nbc_market.databinding.LayoutRecyclerBinding
import java.text.NumberFormat

class RecyclerAdapter(val list: List<RecyclerItemDataClass>) : RecyclerView.Adapter<RecyclerAdapter.Holder>() {

    inner class Holder(binding: LayoutRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        var root = binding.root
        var imgThing = binding.ivThing
        var txtThing = binding.tvTitle
        var txtAddress = binding.tvAddress
        var txtPrice = binding.tvPrice
        var imgChat = binding.ivChat
        var txtChat = binding.tvChat
        var imgLike = binding.ivLike
        var txtLike = binding.tvLike
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.Holder {
        val binding = LayoutRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.Holder, position: Int) {
        holder.imgThing.setImageResource(list[position].img)
        holder.txtThing.text = list[position].usedThing
        holder.txtAddress.text = list[position].address
        holder.txtPrice.text = NumberFormat.getInstance().format(list[position].price) + "원"
        holder.imgChat.setImageResource(R.drawable.chat)
        holder.txtChat.text = list[position].chat.toString()
        holder.imgLike.setImageResource(R.drawable.heart)
        holder.txtLike.text = list[position].like.toString()
        holder.root.setOnClickListener {
            itemClick?.onClick(it, position)
        }
    }

    override fun getItemCount(): Int = list.size

    override fun getItemId(position: Int) = list.size.toLong()

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

}