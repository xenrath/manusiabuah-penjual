package com.xenrath.manusiabuah.ui.home.tabs.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.Constant
import com.xenrath.manusiabuah.data.model.product.DataProduct
import com.xenrath.manusiabuah.utils.CurrencyHelper
import com.xenrath.manusiabuah.utils.GlideHelper

class ProductAdapter(
    val context: Context,
    var product: ArrayList<DataProduct>,
    val clickListener: (DataProduct, Int, String) -> Unit
    ): RecyclerView.Adapter<ProductAdapter.Holder>() {

    class Holder(view: View): RecyclerView.ViewHolder(view) {
        val ivImage = view.findViewById<ImageView>(R.id.iv_image)!!
        val tvName = view.findViewById<TextView>(R.id.tv_name)!!
        val tvPrice = view.findViewById<TextView>(R.id.tv_price)!!
        val tvStock = view.findViewById<TextView>(R.id.tv_stock)!!
        val layoutProduct = view.findViewById<LinearLayout>(R.id.layout_product)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.adapter_product, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val product = product[position]

        GlideHelper.setImage(context, product.image!!, holder.ivImage)
        holder.tvName.text = product.name
        holder.tvPrice.text = CurrencyHelper.changeToRupiah(product.price.toString())
        holder.tvStock.text = product.stock.toString()
//        holder.cvProduct.setOnClickListener {
//            Constant.PRODUCT_ID = dataProduct[position].id!!
//            clickListener(dataProduct[position], position, "detail")
//        }
        holder.layoutProduct.setOnClickListener {
            val popupMenu = PopupMenu(context, holder.layoutProduct)
            popupMenu.inflate(R.menu.menu_options)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_update -> {
                        Constant.PRODUCT_ID = product.id!!
                        clickListener(product, position, "update")
                    }
                    R.id.action_delete -> {
                        Constant.PRODUCT_ID = product.id!!
                        clickListener(product, position, "delete")
                    }
                }
                true
            }
            popupMenu.show()
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

    fun removeProduct(position: Int) {
        product.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, product.size)
    }

}