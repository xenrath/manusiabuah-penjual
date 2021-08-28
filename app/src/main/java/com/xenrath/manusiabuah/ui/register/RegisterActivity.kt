package com.xenrath.manusiabuah.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.model.user.ResponseUser
import com.xenrath.manusiabuah.ui.login.LoginActivity
import com.xenrath.manusiabuah.utils.sweetalert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_product_create.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.et_name

class RegisterActivity : AppCompatActivity(), RegisterContract.View {

    lateinit var presenter: RegisterPresenter

    private lateinit var sLoading: SweetAlertDialog
    private lateinit var sSuccess: SweetAlertDialog
    private lateinit var sError: SweetAlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        presenter = RegisterPresenter(this)
    }

    override fun initActivity() {
        sLoading = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError = SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal!")
    }

    override fun initListener() {
        btn_register.setOnClickListener {
            val name = et_name.text
            val email = et_email.text
            val password = et_password.text
            val passwordConfirmation = et_password_confirmation.text
            val phone = et_phone.text
            when {
                name!!.isEmpty() -> {
                    validationError(et_name, "Nama tidak boleh kosong!")
                }
                email!!.isEmpty() -> {
                    validationError(et_price, "Email tidak boleh kosong!")
                }
                password!!.isEmpty() -> {
                    validationError(et_address, "Password tidak boleh kosong!")
                }
                passwordConfirmation!!.isEmpty() -> {
                    validationError(et_address, "Konfirmasi password tidak boleh kosong!")
                }
                phone!!.isEmpty() -> {
                    validationError(et_address, "Nomor telepon tidak boleh kosong!")
                }
                else -> {
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
    }

    override fun onLoading(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }

    override fun onResult(responseUser: ResponseUser) {
        if (responseUser.status) {
            showAlertSuccess(responseUser.message)
            finish()
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            showAlertError(responseUser.message)
        }
    }

    override fun showAlertSuccess(message: String) {
        sSuccess
            .setContentText(message)
            .setConfirmText("OK")
            .setConfirmClickListener {
                it.dismissWithAnimation()
                finish()
            }
            .show()
    }

    override fun showAlertError(message: String) {
        sError
            .setContentText(message)
            .setConfirmText("OK")
            .setConfirmClickListener {
                it.dismissWithAnimation()
            }
            .show()
    }

    override fun validationError(editText: EditText, message: String) {
        editText.error = message
        editText.requestFocus()
    }
}