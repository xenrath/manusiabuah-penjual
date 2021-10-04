package com.xenrath.manusiabuah.ui.payment

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.model.account.DataAccount
import com.xenrath.manusiabuah.utils.GlideHelper

class PaymentAdapter(
    var context: Context,
    var account: ArrayList<DataAccount>
) : RecyclerView.Adapter<PaymentAdapter.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_account_name)!!
        val tvNumber = view.findViewById<TextView>(R.id.tv_account_number)!!
        val ivBank = view.findViewById<ImageView>(R.id.iv_bank_image)!!
        val ivCopyAccount = view.findViewById<ImageView>(R.id.iv_copy_account)!!
        val layoutAccount = view.findViewById<LinearLayout>(R.id.layout_account)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_account, parent, false)
        return Holder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val account = account[position]
        val bank = account.bank!!
        holder.tvName.text = account.name
        holder.tvNumber.text = account.number
        GlideHelper.setImage(context, bank.image!!, holder.ivBank)
        holder.ivCopyAccount.setOnClickListener {
            val copyManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val copyText = ClipData.newPlainText("text", account.number)
            copyManager.setPrimaryClip(copyText)

            Toast.makeText(context, "Nomor rekening berhasil di salin", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return account.size
    }

    fun setData(newDataAccount: List<DataAccount>) {
        account.clear()
        account.addAll(newDataAccount)
        notifyDataSetChanged()
    }
}