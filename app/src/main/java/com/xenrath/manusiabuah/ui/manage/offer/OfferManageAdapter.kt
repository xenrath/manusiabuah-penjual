package com.xenrath.manusiabuah.ui.manage.offer

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
import com.xenrath.manusiabuah.ui.manage.offer.detail.OfferManageDetailActivity
import com.xenrath.manusiabuah.utils.CurrencyHelper

class OfferManageAdapter(
    val context: Context,
    val offer: ArrayList<DataOffer>
) : RecyclerView.Adapter<OfferManageAdapter.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val tvProductName = view.findViewById<TextView>(R.id.tv_product_name)!!
        val tvTotalItem = view.findViewById<TextView>(R.id.tv_total_item)!!
        val tvPrice = view.findViewById<TextView>(R.id.tv_price)!!
        val tvPriceOffer = view.findViewById<TextView>(R.id.tv_price_offer)!!
        val tvStatus = view.findViewById<TextView>(R.id.tv_status)!!
        val layoutBargain = view.findViewById<LinearLayout>(R.id.layout_bargain)!!
        val layoutUser = view.findViewById<LinearLayout>(R.id.layout_user)!!
        val layoutStatus = view.findViewById<LinearLayout>(R.id.layout_status)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_manage_bargain, parent, false)
        return Holder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val bargain = offer[position]
        val product = bargain.product!!

        holder.tvProductName.text = product.name
        holder.tvPrice.text = CurrencyHelper.changeToRupiah(bargain.price!!)
        holder.tvPrice.paintFlags = holder.tvPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        holder.tvPriceOffer.text = CurrencyHelper.changeToRupiah(bargain.price_offer!!)
        holder.tvTotalItem.text = "${bargain.total_item} (kg)"
        holder.tvStatus.text = bargain.status

        var color = context.getColor(R.color.wait)
        when (bargain.status) {
            "Menunggu" -> color = context.getColor(R.color.customWarning)
            "Diterima" -> color = context.getColor(R.color.customPrimary)
            "Dibatalkan" -> color = context.getColor(R.color.customDanger)
            "Ditolak" -> color = context.getColor(R.color.customDanger)
            "Selesai" -> color = context.getColor(R.color.customSuccess)
        }
        when (bargain.status) {
            "Menunggu" -> {
                holder.layoutStatus.visibility = View.GONE
                holder.layoutUser.visibility = View.VISIBLE
            }
            else -> {
                holder.layoutStatus.visibility = View.VISIBLE
                holder.layoutUser.visibility = View.GONE
            }
        }
        holder.tvStatus.setTextColor(color)

        holder.layoutBargain.setOnClickListener {
            Constant.BARGAIN_ID = bargain.id!!
            context.startActivity(Intent(context, OfferManageDetailActivity::class.java))
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