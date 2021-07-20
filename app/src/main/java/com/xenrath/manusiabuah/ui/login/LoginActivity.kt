package com.xenrath.manusiabuah.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.ResponseLogin
import com.xenrath.manusiabuah.data.database.PrefManager
import com.xenrath.manusiabuah.ui.MainActivity
import com.xenrath.manusiabuah.ui.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginContract.View {

    lateinit var presenter: LoginPresenter
    lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter = LoginPresenter(this)
        prefManager = PrefManager(this)
    }

    override fun initActivity() {
    }

    override fun initListener() {
        btn_login.setOnClickListener {
            presenter.doLogin(
                et_email.text.toString(),
                et_password.text.toString(),
                "Seller"
            )
        }
        btn_to_register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    override fun onLoading(loading: Boolean) {
        when(loading) {
            true -> {
                pb_login.visibility = View.VISIBLE
            }
            false -> {
                pb_login.visibility = View.GONE
            }
        }
    }

    override fun onResult(responseLogin: ResponseLogin) {
        presenter.setPref(prefManager, responseLogin.user!!)
        finish()
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}