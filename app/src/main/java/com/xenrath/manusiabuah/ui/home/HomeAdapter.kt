package com.xenrath.manusiabuah.ui.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.Constant
import com.xenrath.manusiabuah.data.model.product.DataProduct
import com.xenrath.manusiabuah.ui.home.detail.HomeDetailActivity
import com.xenrath.manusiabuah.utils.CurrencyHelper
import com.xenrath.manusiabuah.utils.GlideHelper

class HomeAdapter(
    var context: Context,
    var product: ArrayList<DataProduct>
) : RecyclerView.Adapter<HomeAdapter.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_name)!!
        val tvPrice = view.findViewById<TextView>(R.id.tv_price)!!
        val tvAddress = view.findViewById<TextView>(R.id.tv_address)!!
        val imgProduct = view.findViewById<ImageView>(R.id.iv_product)!!
        val layoutProduct = view.findViewById<LinearLayout>(R.id.layout_product)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_home, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val product = product[position]
        holder.tvName.text = product.name
        holder.tvPrice.text = CurrencyHelper.changeToRupiah(product.price.toString())
        holder.tvAddress.text = product.address
        GlideHelper.setImage(context, product.image!!, holder.imgProduct)
        holder.layoutProduct.setOnClickListener {
            Constant.PRODUCT_ID = product.id!!
            context.startActivity(Intent(context, HomeDetailActivity::class.java))
        }
    }

    override fun getItemCount(): Int {
        return product.size
    }

    fun setData(newDataProduct: List<DataProduct>) {
        product.clear()
        product.addAll(newDataProduct)
        notifyDataSetChanged()
    }

}