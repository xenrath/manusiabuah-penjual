package com.xenrath.manusiabuah.ui.home.detail

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.model.comment.DataComment
import com.xenrath.manusiabuah.utils.GlideHelper

class CommentAdapter(
    var context: Context,
    var comment: ArrayList<DataComment>
) : RecyclerView.Adapter<CommentAdapter.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val ivUserImage = view.findViewById<ImageView>(R.id.iv_user_image)!!
        val tvUserName = view.findViewById<TextView>(R.id.tv_user_name)!!
        val tvComment = view.findViewById<TextView>(R.id.tv_comment)!!

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_comment, parent, false)
        return Holder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val comment = comment[position]
        val transaction = comment.transaction!!
        val user = transaction.user!!

        GlideHelper.setImage(context, user.image!!, holder.ivUserImage)
        holder.tvUserName.text = user.name
        holder.tvComment.text = comment.comment
    }

    override fun getItemCount(): Int {
        return comment.size
    }

    fun setData(newDataComment: List<DataComment>) {
        comment.clear()
        comment.addAll(newDataComment)
        notifyDataSetChanged()
    }
}