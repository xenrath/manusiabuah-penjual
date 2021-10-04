package com.xenrath.manusiabuah.ui.address

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.Constant
import com.xenrath.manusiabuah.data.model.address.DataAddress

class AddressAdapter(
    var context: Context,
    var address: ArrayList<DataAddress>,
    var check: Listener,
    val clickListener: (DataAddress, Int, String) -> Unit
): RecyclerView.Adapter<AddressAdapter.Holder>() {

    class Holder(view: View): RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_name)!!
        val tvPhone = view.findViewById<TextView>(R.id.tv_phone)!!
        val tvAddress = view.findViewById<TextView>(R.id.tv_address)!!
        val layoutAddress = view.findViewById<LinearLayout>(R.id.layout_address)!!
        val rbAddress = view.findViewById<RadioButton>(R.id.rb_address)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.adapter_address, parent, false)
        return Holder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val address = address[position]
        holder.tvName.text = address.name
        holder.tvPhone.text = address.phone
        holder.tvAddress.text = address.address + ", " + address.city_name + ", " + address.postal_code + ", (" + address.place + ")"
        holder.rbAddress.isChecked = address.status != 0
        holder.rbAddress.setOnClickListener {
            check.onClick(address)
        }
        holder.layoutAddress.setOnClickListener {
            val popupMenu = PopupMenu(context, holder.layoutAddress)
            popupMenu.inflate(R.menu.menu_options)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_update -> {
                        Constant.ADDRESS_ID = address.id!!
                        clickListener(address, position, "update")
                    }
                    R.id.action_delete -> {
                        Constant.ADDRESS_ID = address.id!!
                        clickListener(address, position, "delete")
                    }
                }
                true
            }
            popupMenu.show()
        }
    }

    override fun getItemCount(): Int {
        return address.size
    }

    interface Listener {
        fun onClick(address: DataAddress)
    }

    fun setData(newDataAddress: List<DataAddress>) {
        address.clear()
        address.addAll(newDataAddress)
        notifyDataSetChanged()
    }

    fun removeProduct(position: Int) {
        address.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, address.size)
    }

}