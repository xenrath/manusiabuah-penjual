package com.xenrath.manusiabuah.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.ResponseLogin
import com.xenrath.manusiabuah.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), RegisterContract.View {

    lateinit var presenter: RegisterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        presenter = RegisterPresenter(this)
    }

    override fun initListener() {
        btn_register.setOnClickListener {
            val name = et_name.text
            val email = et_email.text
            val password = et_password.text
            val passwordConfirmation = et_password_confirmation.text
            val phone = et_phone.text
            if (
                name.isNullOrEmpty() ||
                email.isNullOrEmpty() ||
                password.isNullOrEmpty() ||
                phone.isNullOrEmpty()
            ) {
                showMessage("Lengkapi data dengan benar")
            } else {
                presenter.doRegister(
                    name.toString(),
                    email.toString(),
                    password.toString(),
                    passwordConfirmation.toString(),
                    phone.toString(),
                    "Seller"
                )
            }
        }
        btn_to_login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    override fun onLoading(loading: Boolean) {
        when (loading) {
            true -> {
                progress_bar.visibility = View.VISIBLE
            }
            false -> {
                progress_bar.visibility = View.GONE
            }
        }
    }

    override fun onResult(responseLogin: ResponseLogin) {
        showMessage(responseLogin.message)
        finish()
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}