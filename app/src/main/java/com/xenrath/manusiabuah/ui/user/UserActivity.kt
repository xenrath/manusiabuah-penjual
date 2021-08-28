package com.xenrath.manusiabuah.ui.user

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.Constant
import com.xenrath.manusiabuah.data.model.user.ResponseUser
import com.xenrath.manusiabuah.utils.GlideHelper
import com.xenrath.manusiabuah.utils.sweetalert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_user.*
import kotlinx.android.synthetic.main.toolbar_custom.*

class UserActivity : AppCompatActivity(), UserContract.View {

    lateinit var presenter: UserPresenter
    lateinit var sLoading: SweetAlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        presenter = UserPresenter(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.getUser(Constant.USER_ID)
    }

    @SuppressLint("SetTextI18n")
    override fun initActivity() {
        tv_title.text = "Detail Petani"
    }

    override fun initListener() {
        iv_back.setOnClickListener {
            onBackPressed()
        }
        iv_help.setOnClickListener {

        }
    }

    override fun onLoading(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }

    override fun onResult(responseUser: ResponseUser) {
        val user = responseUser.user

        GlideHelper.setImage(this, user.image!!, iv_image)
        tv_name.text = user.name
        tv_status.text = user.level
        tv_email.text = user.email
        tv_phone.text = user.phone
        tv_address.text = user.address

    }

    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}