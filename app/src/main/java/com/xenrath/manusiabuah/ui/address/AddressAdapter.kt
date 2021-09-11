package com.xenrath.manusiabuah.ui.address

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.model.address.DataAddress

class AddressAdapter(
    var context: Context,
    var address: ArrayList<DataAddress>,
    val clickListener: (DataAddress) -> Unit
): RecyclerView.Adapter<AddressAdapter.Holder>() {

    class Holder(view: View): RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_name)!!
        val tvPhone = view.findViewById<TextView>(R.id.tv_phone)!!
        val tvAddress = view.findViewById<TextView>(R.id.tv_address)!!
        val layoutAddress = view.findViewById<CardView>(R.id.layout_address)!!
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
        holder.layoutAddress.setOnClickListener {
            clickListener(address)
        }
    }

    override fun getItemCount(): Int {
        return address.size
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