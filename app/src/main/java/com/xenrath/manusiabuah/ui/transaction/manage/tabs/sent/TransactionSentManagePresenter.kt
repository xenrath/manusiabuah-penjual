package com.xenrath.manusiabuah.ui.transaction.manage.tabs.sent

import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionList
import com.xenrath.manusiabuah.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransactionSentManagePresenter(val view: TransactionSentManageContract.View) :
    TransactionSentManageContract.Presenter {
    override fun transactionSentManage(id: Long) {
        view.onLoading(true, "Menampilkan pesanan...")
        ApiService.endPoint.transactionSentManage(id).enqueue(object : Callback<ResponseTransactionList> {
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