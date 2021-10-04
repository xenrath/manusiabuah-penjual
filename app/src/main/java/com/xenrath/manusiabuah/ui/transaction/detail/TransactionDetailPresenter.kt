package com.xenrath.manusiabuah.ui.transaction.detail

import com.xenrath.manusiabuah.data.model.comment.ResponseCommentDetail
import com.xenrath.manusiabuah.data.model.comment.ResponseCommentUpdate
import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionDetail
import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionUpdate
import com.xenrath.manusiabuah.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransactionDetailPresenter(val view: TransactionDetailContract.View) :
    TransactionDetailContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
    }

    override fun transactionDetail(id: Long) {
        view.onLoading(true, "Menampilkan detail pembelian...")
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

    override fun transactionAccepted(id: Long) {
        view.onLoading(true, "Menerima pesanan...")
        ApiService.endPoint.transactionAccepted(id, "PUT")
            .enqueue(object : Callback<ResponseTransactionUpdate> {
                override fun onResponse(
                    call: Call<ResponseTransactionUpdate>,
                    response: Response<ResponseTransactionUpdate>
                ) {
                    view.onLoading(false)
                    if (response.isSuccessful) {
                        val responseTransactionUpdate: ResponseTransactionUpdate? = response.body()
                        view.onResultTransactionUpdate(responseTransactionUpdate!!)
                    }
                }

                override fun onFailure(call: Call<ResponseTransactionUpdate>, t: Throwable) {
                    view.onLoading(false)
                }
            })
    }

    override fun commentCheck(id: Long) {
        view.onLoading(true, "Mengecek data...")
        ApiService.endPoint.commentCheck(id)
            .enqueue(object : Callback<ResponseCommentDetail> {
                override fun onResponse(
                    call: Call<ResponseCommentDetail>,
                    response: Response<ResponseCommentDetail>
                ) {
                    view.onLoading(false)
                    if (response.isSuccessful) {
                        val responseCommentDetail: ResponseCommentDetail? = response.body()
                        view.onResultCommentCheck(responseCommentDetail!!)
                    }
                }

                override fun onFailure(call: Call<ResponseCommentDetail>, t: Throwable) {
                    view.onLoading(false)
                }
            })
    }

    override fun commentCreate(user_id: String, product_id: String, comment: String) {
        view.onLoading(true, "Mengirim komentar...")
        ApiService.endPoint.commentCreate(user_id, product_id, comment)
            .enqueue(object : Callback<ResponseCommentUpdate> {
                override fun onResponse(
                    call: Call<ResponseCommentUpdate>,
                    response: Response<ResponseCommentUpdate>
                ) {
                    view.onLoading(false)
                    if (response.isSuccessful) {
                        val responseCommentUpdate: ResponseCommentUpdate? = response.body()
                        view.onResultCommentUpdate(responseCommentUpdate!!)
                    }
                }

                override fun onFailure(call: Call<ResponseCommentUpdate>, t: Throwable) {
                    view.onLoading(false)
                }
            })
    }
}