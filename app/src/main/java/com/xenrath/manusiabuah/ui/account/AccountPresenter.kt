package com.xenrath.manusiabuah.ui.account

import com.xenrath.manusiabuah.data.model.account.ResponseAccountList
import com.xenrath.manusiabuah.data.model.account.ResponseAccountUpdate
import com.xenrath.manusiabuah.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountPresenter(val view: AccountContract.View) : AccountContract.Presenter {
    init {
        view.initActivity()
        view.initListener()
    }

    override fun accountList(id: Long) {
        view.onLoading(true, "Menampilkan rekening...")
        ApiService.endPoint.accountList(id).enqueue(object : Callback<ResponseAccountList> {
            override fun onResponse(
                call: Call<ResponseAccountList>,
                response: Response<ResponseAccountList>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseAccountList: ResponseAccountList? = response.body()
                    view.onResultList(responseAccountList!!)
                }
            }

            override fun onFailure(call: Call<ResponseAccountList>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun accountDelete(id: Long) {
        view.onLoading(true, "Menghapus rekening...")
        ApiService.endPoint.accountDelete(id).enqueue(object : Callback<ResponseAccountUpdate> {
            override fun onResponse(
                call: Call<ResponseAccountUpdate>,
                response: Response<ResponseAccountUpdate>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseAccountUpdate: ResponseAccountUpdate? = response.body()
                    view.onResultDelete(responseAccountUpdate!!)
                }
            }

            override fun onFailure(call: Call<ResponseAccountUpdate>, t: Throwable) {
                view.onLoading(false)
            }
        })
    }
}