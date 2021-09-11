package com.xenrath.manusiabuah.ui.register

import com.xenrath.manusiabuah.data.model.user.ResponseUser
import com.xenrath.manusiabuah.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterPresenter(val view: RegisterContract.View) : RegisterContract.Presenter {

    init {
        view.initActivity()
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
        view.onLoading(true, "Melakukan pendaftaran...")
        ApiService.endPoint.userRegister(
            name,
            email,
            password,
            password_confirmation,
            phone,
            level
        ).enqueue(object : Callback<ResponseUser> {
            override fun onResponse(
                call: Call<ResponseUser>,
                response: Response<ResponseUser>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseUser: ResponseUser? = response.body()
                    view.onResult(responseUser!!)
                }
            }

            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                view.onLoading(true)
            }
        })
    }
}