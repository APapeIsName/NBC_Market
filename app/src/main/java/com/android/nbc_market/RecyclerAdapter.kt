package com.android.nbc_market

import android.content.ClipData.Item
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.android.nbc_market.databinding.LayoutRecyclerBinding
import java.text.NumberFormat

class RecyclerAdapter(val context: Context) : RecyclerView.Adapter<RecyclerAdapter.Holder>() {

    var itemSize = RecyclerItems.listData.size

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
        holder.imgThing.setImageResource(RecyclerItems.listData[position].img)
        holder.txtThing.text = RecyclerItems.listData[position].usedThing
        holder.txtAddress.text = RecyclerItems.listData[position].address
        holder.txtPrice.text = NumberFormat.getInstance().format(RecyclerItems.listData[position].price) + "원"
        holder.imgChat.setImageResource(R.drawable.chat)
        holder.txtChat.text = RecyclerItems.listData[position].chat.toString()
        if(RecyclerItems.listData[position].isLiked) holder.imgLike.setImageResource(R.drawable.full_heart)
        else holder.imgLike.setImageResource(R.drawable.heart)
        holder.txtLike.text = RecyclerItems.listData[position].like.toString()
        holder.root.setOnClickListener {
            itemClick?.onClick(it, position)
        }
        holder.root.setOnLongClickListener {
            createDeleteDialog(position)
            true
        }
    }

    override fun getItemCount(): Int = RecyclerItems.listData.size

    override fun getItemId(position: Int) = position.toLong()

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    var itemLongClick: ItemClick? = null

    private fun createDeleteDialog(position: Int) {
        val dialog = AlertDialog.Builder(context)
        dialog.setTitle("상품 삭제")
        dialog.setIcon(R.drawable.chat)
        dialog.setMessage("상품을 정말로 삭제하시겠습니까?")

        val clickListener = DialogInterface.OnClickListener { _, which ->
            when(which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    RecyclerItems.listData.removeAt(position)
                    println("${RecyclerItems.listData.size} dwdw")
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, itemCount)
                }
            }
        }

        dialog.setPositiveButton("확인", clickListener)
        dialog.setNegativeButton("취소", clickListener)

        dialog.show()
    }

}