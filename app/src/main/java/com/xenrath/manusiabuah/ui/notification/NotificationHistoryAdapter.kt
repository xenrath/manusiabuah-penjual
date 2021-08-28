package com.xenrath.manusiabuah.ui.notification

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.model.bargain.DataBargain

class NotificationHistoryAdapter(
    var context: Context,
    var bargain: ArrayList<DataBargain>
    ): RecyclerView.Adapter<NotificationHistoryAdapter.Holder>() {

    class Holder(view: View): RecyclerView.ViewHolder(view) {
        val tvUser = view.findViewById<TextView>(R.id.tv_user)!!
        val tvProduct = view.findViewById<TextView>(R.id.tv_product)!!
        val tvPrice = view.findViewById<TextView>(R.id.tv_price)!!
        val tvPriceOffer = view.findViewById<TextView>(R.id.tv_price_offer)!!
        val tvStatus = view.findViewById<TextView>(R.id.tv_status)!!
//        val layoutOrder = view.findViewById<LinearLayout>(R.id.layout_order)!!
//        val cvHistory = view.findViewById<CardView>(R.id.cv_history)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.adapter_notification_history, parent, false)
        return Holder(view)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val bargain = bargain[position]
        holder.tvUser.text = bargain.user_id
        holder.tvProduct.text = bargain.product_id
        holder.tvPrice.text = bargain.price
        holder.tvPriceOffer.text = bargain.price_offer

        holder.tvStatus.text = bargain.status
        var color = context.getColor(R.color.wait)
        if (bargain.status == "Diterima") color = context.getColor(R.color.done)
        else if (bargain.status == "Ditolak") color = context.getColor(R.color.cancel)
        holder.tvStatus.setTextColor(color)
//        if (bargain.status == "Diterima") {
//            holder.layoutOrder.visibility = View.VISIBLE
//        }
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