package com.xenrath.manusiabuah.ui.transaction

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.Constant
import com.xenrath.manusiabuah.data.model.transaction.DataTransaction
import com.xenrath.manusiabuah.ui.payment.PaymentActivity
import com.xenrath.manusiabuah.ui.transaction.detail.TransactionDetailActivity

class TransactionAdapter(
    var context: Context,
    var transaction: ArrayList<DataTransaction>
) : RecyclerView.Adapter<TransactionAdapter.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val tvProductName = view.findViewById<TextView>(R.id.tv_product_name)!!
        val tvTotalItemPrice = view.findViewById<TextView>(R.id.tv_product_price)!!
        val tvAddress = view.findViewById<TextView>(R.id.tv_address)!!
        val tvCourierServiceType = view.findViewById<TextView>(R.id.tv_courier_service_type)!!
        val layoutStatus = view.findViewById<LinearLayout>(R.id.layout_status)!!
        val tvStatus = view.findViewById<TextView>(R.id.tv_status)!!
        val layoutInvoice = view.findViewById<LinearLayout>(R.id.layout_invoice)!!
        val tvInvoice = view.findViewById<TextView>(R.id.tv_invoice)!!
        val layoutTransaction = view.findViewById<LinearLayout>(R.id.layout_transaction)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_transaction, parent, false)
        return Holder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val transaction = transaction[position]
        val product = transaction.product!!

        holder.tvProductName.text = product.name
        holder.tvTotalItemPrice.text = "${transaction.total_item} x ${transaction.price}"
        holder.tvAddress.text =
            "${product.city_name} ${product.province_name} - ${transaction.origin}"
        holder.tvCourierServiceType.text = transaction.courier + transaction.service_type
        var color = context.getColor(R.color.wait)
        when (transaction.status) {
            "Selesai" -> {
                holder.layoutStatus.visibility = View.VISIBLE
                holder.layoutInvoice.visibility = View.GONE
                color = context.getColor(R.color.customSuccess)
            }
            else -> {
                holder.layoutInvoice.visibility = View.VISIBLE
                holder.layoutStatus.visibility = View.GONE
            }
        }
        holder.tvStatus.text = transaction.status
        holder.tvStatus.setTextColor(color)
        holder.tvInvoice.text = transaction.invoice_number
        holder.layoutTransaction.setOnClickListener {
            when (transaction.status) {
                "Belum dibayar" -> {
                    Constant.TRANSACTION_ID = transaction.id!!
                    context.startActivity(Intent(context, PaymentActivity::class.java))
                }
                else -> {
                    Constant.TRANSACTION_ID = transaction.id!!
                    context.startActivity(Intent(context, TransactionDetailActivity::class.java))
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return transaction.size
    }

    fun setData(newDataTransaction: List<DataTransaction>) {
        transaction.clear()
        transaction.addAll(newDataTransaction)
        notifyDataSetChanged()
    }
}