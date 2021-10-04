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
import com.xenrath.manusiabuah.data.model.offer.DataOffer

class NotificationAdapter(
    var context: Context,
    var offer: ArrayList<DataOffer>,
    val clickListener: (DataOffer, Int, String) -> Unit
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
        val bargain = offer[position]
//        holder.tvUser.text = bargain.user_name
//        holder.tvProduct.text = bargain.product_name
        holder.tvPrice.text = bargain.price
        holder.tvPriceOffer.text = bargain.price_offer
        holder.btnReject.setOnClickListener {
            Constant.OFFER_ID = bargain.id!!
            clickListener(bargain, position, "reject")
        }
        holder.btnAccept.setOnClickListener {
            Constant.OFFER_ID = bargain.id!!
            clickListener(bargain, position, "accept")
        }
    }

    override fun getItemCount(): Int {
        return offer.size
    }

    fun setData(newDataOffer: List<DataOffer>) {
        offer.clear()
        offer.addAll(newDataOffer)
        notifyDataSetChanged()
    }

    fun removeProduct(position: Int) {
        offer.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, offer.size)
    }

}