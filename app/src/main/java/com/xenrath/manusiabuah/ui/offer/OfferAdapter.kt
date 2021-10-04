package com.xenrath.manusiabuah.ui.offer

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.Constant
import com.xenrath.manusiabuah.data.model.offer.DataOffer
import com.xenrath.manusiabuah.ui.offer.detail.OfferDetailActivity
import com.xenrath.manusiabuah.utils.CurrencyHelper

class OfferAdapter(
    var context: Context,
    var offer: ArrayList<DataOffer>
) : RecyclerView.Adapter<OfferAdapter.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val tvProductName = view.findViewById<TextView>(R.id.tv_product_name)!!
        val tvTotalItem = view.findViewById<TextView>(R.id.tv_total_item)!!
        val tvPrice = view.findViewById<TextView>(R.id.tv_price)!!
        val tvPriceOffer = view.findViewById<TextView>(R.id.tv_price_offer)!!
        val tvStatus = view.findViewById<TextView>(R.id.tv_status)!!
        val layoutBargain = view.findViewById<LinearLayout>(R.id.layout_transaction)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_my_bargain, parent, false)
        return Holder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val offer = offer[position]
        val product = offer.product!!

        holder.tvProductName.text = product.name
        holder.tvPrice.text = CurrencyHelper.changeToRupiah(offer.price!!)
        holder.tvPrice.paintFlags = holder.tvPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        holder.tvPriceOffer.text = CurrencyHelper.changeToRupiah(offer.price_offer!!)
        holder.tvTotalItem.text = "${offer.total_item} (kg)"
        holder.tvStatus.text = offer.status

        var color = context.getColor(R.color.wait)
        when (offer.status) {
            "Menunggu" -> color = context.getColor(R.color.customWarning)
            "Diterima" -> color = context.getColor(R.color.customPrimary)
            "Dibatalkan" -> color = context.getColor(R.color.customDanger)
            "Ditolak" -> color = context.getColor(R.color.customDanger)
            "Selesai" -> color = context.getColor(R.color.customSuccess)
        }
        holder.tvStatus.setTextColor(color)

        holder.layoutBargain.setOnClickListener {
            Constant.OFFER_ID = offer.id!!
            context.startActivity(Intent(context, OfferDetailActivity::class.java))
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

}