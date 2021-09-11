package com.xenrath.manusiabuah.ui.account

import android.annotation.SuppressLint
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
import com.xenrath.manusiabuah.data.model.account.DataAccount
import com.xenrath.manusiabuah.utils.GlideHelper

class AccountAdapter(
    var context: Context,
    var account: ArrayList<DataAccount>,
    val clickListener: (DataAccount, Int, String) -> Unit
): RecyclerView.Adapter<AccountAdapter.Holder>() {

    class Holder(view: View): RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_account_name)!!
        val tvNumber = view.findViewById<TextView>(R.id.tv_account_number)!!
        val ivBank = view.findViewById<ImageView>(R.id.iv_bank_image)!!
        val ivCopyAccount = view.findViewById<ImageView>(R.id.iv_copy_account)!!
        val layoutAccount = view.findViewById<LinearLayout>(R.id.layout_account)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.adapter_account, parent, false)
        return Holder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val account = account[position]
        val bank = account.bank!!
        holder.tvName.text = account.name
        holder.tvNumber.text = account.number
        GlideHelper.setImage(context, bank.image!!, holder.ivBank)
        holder.ivCopyAccount.visibility = View.GONE
        holder.layoutAccount.setOnClickListener {
            val popupMenu = PopupMenu(context, holder.layoutAccount)
            popupMenu.inflate(R.menu.menu_options)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_update -> {
                        Constant.ACCOUNT_ID = account.id!!
                        clickListener(account, position, "update")
                    }
                    R.id.action_delete -> {
                        Constant.ACCOUNT_ID = account.id!!
                        clickListener(account, position, "delete")
                    }
                }
                true
            }
            popupMenu.show()
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

    fun removeAccount(position: Int) {
        account.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, account.size)
    }

}