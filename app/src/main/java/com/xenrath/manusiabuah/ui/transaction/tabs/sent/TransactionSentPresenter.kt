package com.xenrath.manusiabuah.ui.transaction.tabs.sent

import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionList
import com.xenrath.manusiabuah.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransactionSentPresenter(val view: TransactionSentContract.View) :
    TransactionSentContract.Presenter {
    override fun transactionSent(id: Long) {
        view.onLoading(true, "Menampilkan pembelian...")
        ApiService.endPoint.transactionSent(id).enqueue(object : Callback<ResponseTransactionList> {
            override fun onResponse(
                call: Call<ResponseTransactionList>,
                response: Response<ResponseTransactionList>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseTransactionList: ResponseTransactionList? = response.body()
                    view.onResult(responseTransactionList!!)
                }
            }

            override fun onFailure(call: Call<ResponseTransactionList>, t: Throwable) {
                view.onLoading(false)
            }
        })
    }
}