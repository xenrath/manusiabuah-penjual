package com.xenrath.manusiabuah.ui.notification

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.Constant
import com.xenrath.manusiabuah.data.model.bargain.DataBargain

class NotificationAdapter(
    var context: Context,
    var bargain: ArrayList<DataBargain>,
    val clickListener: (DataBargain, Int, String) -> Unit
    ): RecyclerView.Adapter<NotificationAdapter.Holder>() {

    class Holder(view: View): RecyclerView.ViewHolder(view) {
        val tvUser = view.findViewById<TextView>(R.id.tv_user)!!
        val tvProduct = view.findViewById<TextView>(R.id.tv_product)!!
        val tvPrice = view.findViewById<TextView>(R.id.tv_price)!!
        val tvPriceOffer = view.findViewById<TextView>(R.id.tv_price_offer)!!
        val btnReject = view.findViewById<Button>(R.id.btn_reject)!!
        val btnAccept = view.findViewById<Button>(R.id.btn_accept)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.adapter_notification, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val bargain = bargain[position]
//        holder.tvUser.text = bargain.user_name
//        holder.tvProduct.text = bargain.product_name
        holder.tvPrice.text = bargain.price
        holder.tvPriceOffer.text = bargain.price_offer
        holder.btnReject.setOnClickListener {
            Constant.BARGAIN_ID = bargain.id!!
            clickListener(bargain, position, "reject")
        }
        holder.btnAccept.setOnClickListener {
            Constant.BARGAIN_ID = bargain.id!!
            clickListener(bargain, position, "accept")
        }
    }

    override fun getItemCount(): Int {
        return bargain.size
    }

    fun setData(newDataBargain: List<DataBargain>) {
        bargain.clear()
        bargain.addAll(newDataBargain)
        notifyDataSetChanged()
    }

    fun removeProduct(position: Int) {
        bargain.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, bargain.size)
    }

}