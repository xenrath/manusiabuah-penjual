package com.xenrath.manusiabuah.ui.user

import com.xenrath.manusiabuah.data.model.user.ResponseUser
import com.xenrath.manusiabuah.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserPresenter(val view: UserContract.View): UserContract.Presenter {

    override fun getUser(id: Long) {
        view.onLoading(true, "Menampilkan detail petani...")
        ApiService.endPoint.getUserDetail(id).enqueue(object: Callback<ResponseUser> {
            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
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
}