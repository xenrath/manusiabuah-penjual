package com.xenrath.manusiabuah.ui.register

import com.xenrath.manusiabuah.data.ResponseLogin
import com.xenrath.manusiabuah.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterPresenter(val view: RegisterContract.View): RegisterContract.Presenter {

    init {
        view.initListener()
    }

    override fun doRegister(
        name: String,
        email: String,
        password: String,
        password_confirmation: String,
        phone: String,
        level: String
    ) {
        view.onLoading(true)
        ApiService.endPoint.registerSeller(
            name,
            email,
            password,
            password_confirmation,
            phone,
            level
        ).enqueue(object : Callback<ResponseLogin>{
            override fun onResponse(
                call: Call<ResponseLogin>,
                response: Response<ResponseLogin>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseLogin: ResponseLogin? = response.body()
                    view.onResult(responseLogin!!)
                }
            }

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                view.onLoading(true)
            }

        })
    }
}