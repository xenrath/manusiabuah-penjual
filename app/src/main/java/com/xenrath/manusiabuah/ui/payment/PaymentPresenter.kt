package com.xenrath.manusiabuah.ui.payment

import com.xenrath.manusiabuah.data.model.account.ResponseAccountList
import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionDetail
import com.xenrath.manusiabuah.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentPresenter(val view: PaymentContract.View) : PaymentContract.Presenter {
    override fun transactionDetail(id: Long) {
        view.onLoading(true, "Menampilkan detail transaksi...")
        ApiService.endPoint.transactionDetail(id)
            .enqueue(object : Callback<ResponseTransactionDetail> {
                override fun onResponse(
                    call: Call<ResponseTransactionDetail>,
                    response: Response<ResponseTransactionDetail>
                ) {
                    view.onLoading(false)
                    if (response.isSuccessful) {
                        val responseTransactionDetail: ResponseTransactionDetail? = response.body()
                        view.onResultTransactionDetail(responseTransactionDetail!!)
                    }
                }

                override fun onFailure(call: Call<ResponseTransactionDetail>, t: Throwable) {
                    view.onLoading(false)
                }
            })
    }

    override fun accountList(user_id: Long) {
        view.onLoadingAccount(true)
        ApiService.endPoint.accountList(user_id).enqueue(object : Callback<ResponseAccountList> {
            override fun onResponse(
                call: Call<ResponseAccountList>,
                response: Response<ResponseAccountList>
            ) {
                view.onLoadingAccount(false)
                if (response.isSuccessful) {
                    val responseAccountList: ResponseAccountList? = response.body()
                    view.onResultAccountList(responseAccountList!!)
                }
            }

            override fun onFailure(call: Call<ResponseAccountList>, t: Throwable) {
                view.onLoadingAccount(false)
            }
        })
    }
}