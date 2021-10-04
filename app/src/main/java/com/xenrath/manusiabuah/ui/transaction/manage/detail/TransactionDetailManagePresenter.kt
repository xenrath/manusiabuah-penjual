package com.xenrath.manusiabuah.ui.transaction.manage.detail

import com.xenrath.manusiabuah.data.model.product.ResponseProductUpdate
import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionDetail
import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionUpdate
import com.xenrath.manusiabuah.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransactionDetailManagePresenter(val view: TransactionDetailManageContract.View) :
    TransactionDetailManageContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
    }

    override fun transactionDetail(id: Long) {
        view.onLoading(true, "Menampilkan detail pesanan...")
        ApiService.endPoint.transactionDetail(id)
            .enqueue(object : Callback<ResponseTransactionDetail> {
                override fun onResponse(
                    call: Call<ResponseTransactionDetail>,
                    response: Response<ResponseTransactionDetail>
                ) {
                    view.onLoading(false)
                    if (response.isSuccessful) {
                        val responseTransactionDetail: ResponseTransactionDetail? = response.body()
                        view.onResultDetail(responseTransactionDetail!!)
                    }
                }

                override fun onFailure(call: Call<ResponseTransactionDetail>, t: Throwable) {
                    view.onLoading(false)
                }
            })
    }

    override fun transactionSend(id: Long) {
        view.onLoading(true, "Mengirim pesanan...")
        ApiService.endPoint.transactionSend(id, "PUT")
            .enqueue(object : Callback<ResponseTransactionUpdate>{
                override fun onResponse(
                    call: Call<ResponseTransactionUpdate>,
                    response: Response<ResponseTransactionUpdate>
                ) {
                    view.onLoading(false)
                    if (response.isSuccessful) {
                        val responseTransactionUpdate: ResponseTransactionUpdate? = response.body()
                        view.onResultUpdate(responseTransactionUpdate!!)
                    }
                }

                override fun onFailure(call: Call<ResponseTransactionUpdate>, t: Throwable) {
                    view.onLoading(false)
                }
            })
    }

    override fun productOut(id: Long, total_item: Int) {
        view.onLoading(true)
        ApiService.endPoint.productOut(id, total_item, "PUT")
            .enqueue(object : Callback<ResponseProductUpdate> {
                override fun onResponse(
                    call: Call<ResponseProductUpdate>,
                    response: Response<ResponseProductUpdate>
                ) {
                    view.onLoading(false)
                    if (response.isSuccessful) {
                        val responseProductUpdate: ResponseProductUpdate? = response.body()
                        view.onResultProduct(responseProductUpdate!!)
                    }
                }

                override fun onFailure(call: Call<ResponseProductUpdate>, t: Throwable) {
                    view.onLoading(false)
                }
            })
    }
}