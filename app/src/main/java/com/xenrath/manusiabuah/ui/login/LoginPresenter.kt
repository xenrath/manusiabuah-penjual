package com.xenrath.manusiabuah.ui.login

import com.xenrath.manusiabuah.data.model.user.DataUser
import com.xenrath.manusiabuah.data.model.user.ResponseUser
import com.xenrath.manusiabuah.data.database.PrefManager
import com.xenrath.manusiabuah.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPresenter(val view: LoginContract.View): LoginContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
        view.onLoading(false)
    }

    override fun doLogin(email: String, password: String, level: String) {
        view.onLoading(true, "Melakukan login...")
        ApiService.endPoint.loginSeller(
            email,
            password,
            level
        ).enqueue(object: Callback<ResponseUser> {
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
                view.onLoading(false)
            }

        })
    }

    override fun setPref(prefManager: PrefManager, dataUser: DataUser) {
        prefManager.prefLogin = true
        prefManager.prefId = dataUser.id!!
        prefManager.prefName = dataUser.name!!
        prefManager.prefEmail = dataUser.email!!
        prefManager.prefPassword = dataUser.password!!
        prefManager.prefPhone = dataUser.phone!!
        prefManager.prefLevel = dataUser.level!!
    }

}