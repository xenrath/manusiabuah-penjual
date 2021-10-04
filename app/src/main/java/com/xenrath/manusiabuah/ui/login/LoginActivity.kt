package com.xenrath.manusiabuah.ui.login

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.model.user.ResponseUser
import com.xenrath.manusiabuah.data.database.PrefManager
import com.xenrath.manusiabuah.data.model.user.DataUser
import com.xenrath.manusiabuah.ui.main.MainActivity
import com.xenrath.manusiabuah.ui.register.RegisterActivity
import com.xenrath.manusiabuah.utils.sweetalert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_login.*
import java.lang.Exception

class LoginActivity : AppCompatActivity(), LoginContract.View, View.OnClickListener {

    lateinit var presenter: LoginPresenter
    lateinit var prefManager: PrefManager

    private lateinit var sLoading: SweetAlertDialog
    private lateinit var sSuccess: SweetAlertDialog
    private lateinit var sError: SweetAlertDialog

    private lateinit var callbackManager: CallbackManager
    private val email = "email"

    private lateinit var fcm: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter = LoginPresenter(this)
        prefManager = PrefManager(this)
    }

    override fun onStart() {
        super.onStart()
        if (prefManager.prefLogin) {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun initActivity() {
        sLoading = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError = SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal!")


    }

    override fun initListener() {
        btn_login.setOnClickListener {
            presenter.userLogin(
                et_email.text.toString(),
                et_password.text.toString(),
                "Seller",
                fcm
            )
        }
        layout_facebook.setOnClickListener(this)
        btn_to_register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    override fun fcmToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("Response", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            fcm = token.toString()

            // Log and toast
            Log.d("Response FCM : ", token.toString())
        })
    }

    override fun onLoading(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }

    override fun onResult(responseUser: ResponseUser) {
        val status: Boolean = responseUser.status
        val message: String = responseUser.message!!

        if (status) {
            val user: DataUser = responseUser.user!!

            presenter.setPref(prefManager, user)
            showAlertSuccess(message)
        } else {
            showAlertError(message)
        }
    }

    override fun showAlertSuccess(message: String) {
        sSuccess
            .setContentText(message)
            .setConfirmText("OK")
            .setConfirmClickListener {
                it.dismissWithAnimation()
                finish()
                startActivity(Intent(this, MainActivity::class.java))
            }
            .show()
    }

    override fun showAlertError(message: String) {
        sError
            .setContentText(message)
            .setConfirmText("OK")
            .setConfirmClickListener {
                it.dismiss()
            }
            .show()
    }

    override fun onClick(view: View?) {
        if (view!!.id == R.id.layout_facebook) {
            login_facebook.performClick()
            login_facebook.setReadPermissions(listOf(email))
            callbackManager = CallbackManager.Factory.create()

            LoginManager.getInstance()
                .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                    override fun onSuccess(result: LoginResult?) {
                        val graphRequest = GraphRequest.newMeRequest(result?.accessToken){obj, response ->
                            try {
                                if (obj.has("id")) {
                                    Log.d("FACEBOOKDATA", obj.getString("name"))
                                    Log.d("FACEBOOKDATA", obj.getString("email"))
                                    Log.d("FACEBOOKDATA", obj.getString("picture"))
                                }
                            } catch (e: Exception) {

                            }
                        }
                        val param = Bundle()
                        param.putString("fields", "name,email,id,picture,type(large)")
                        graphRequest.parameters = param
                        graphRequest.executeAsync()
                    }

                    override fun onCancel() {
                        TODO("Not yet implemented")
                    }

                    override fun onError(error: FacebookException?) {
                        TODO("Not yet implemented")
                    }
                })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}