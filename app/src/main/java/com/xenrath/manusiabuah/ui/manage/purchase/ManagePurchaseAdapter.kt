package com.xenrath.manusiabuah.ui.manage.purchase

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
import com.xenrath.manusiabuah.data.model.bargain.DataBargain
import com.xenrath.manusiabuah.ui.manage.bargain.detail.ManageBargainDetailActivity
import com.xenrath.manusiabuah.utils.CurrencyHelper

class ManagePurchaseAdapter(
    var context: Context,
    var bargain: ArrayList<DataBargain>
) : RecyclerView.Adapter<ManagePurchaseAdapter.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val tvProductName = view.findViewById<TextView>(R.id.tv_product_name)!!
        val tvTotalItem = view.findViewById<TextView>(R.id.tv_total_item)!!
        val tvPrice = view.findViewById<TextView>(R.id.tv_price)!!
        val tvPriceOffer = view.findViewById<TextView>(R.id.tv_price_offer)!!
        val tvStatus = view.findViewById<TextView>(R.id.tv_status)!!
        val layoutBargain = view.findViewById<LinearLayout>(R.id.layout_bargain)!!
        val layoutAction = view.findViewById<LinearLayout>(R.id.layout_action)!!
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
        val bargain = bargain[position]
        val product = bargain.product!!

        holder.tvProductName.text = product.name
        holder.tvPrice.text = CurrencyHelper.changeToRupiah(bargain.price!!)
        holder.tvPrice.paintFlags = holder.tvPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        holder.tvPriceOffer.text = CurrencyHelper.changeToRupiah(bargain.price_offer!!)
        holder.tvTotalItem.text = "${bargain.total_item} (kg)"
        holder.tvStatus.text = bargain.status

        var color = context.getColor(R.color.wait)
        when (bargain.status) {
            "Menunggu" -> {
                color = context.getColor(R.color.customWarning)
                holder.layoutStatus.visibility = View.GONE
                holder.layoutAction.visibility = View.VISIBLE
            }
            "Diterima" -> color = context.getColor(R.color.customPrimary)
            "Dibatalkan" -> color = context.getColor(R.color.customDanger)
            "Ditolak" -> color = context.getColor(R.color.customDanger)
            "Selesai" -> color = context.getColor(R.color.customSuccess)
        }
        holder.tvStatus.setTextColor(color)

        holder.layoutBargain.setOnClickListener {
            Constant.BARGAIN_ID = bargain.id!!
            context.startActivity(Intent(context, ManageBargainDetailActivity::class.java))
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
}