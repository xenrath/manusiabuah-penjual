package com.xenrath.manusiabuah.ui.payment

import com.xenrath.manusiabuah.data.model.account.ResponseAccountList
import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionDetail
import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionUpdate
import com.xenrath.manusiabuah.network.ApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class PaymentPresenter(val view: PaymentContract.View) : PaymentContract.Presenter {
    init {
        view.initActivity()
        view.initListener()
    }

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

    override fun accountList(id: Long) {
        view.onLoadingAccount(true)
        ApiService.endPoint.accountList(id).enqueue(object : Callback<ResponseAccountList> {
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

    override fun transactionProof(id: Long, proof: File) {
        val requestBody: RequestBody = proof.asRequestBody("image/*".toMediaTypeOrNull())
        val multipartBody: MultipartBody.Part =
            MultipartBody.Part.createFormData("proof", proof.name, requestBody)

        view.onLoadingAccount(true)
        ApiService.endPoint.transactionProof(id, multipartBody, "PUT")
            .enqueue(object : Callback<ResponseTransactionUpdate> {
                override fun onResponse(
                    call: Call<ResponseTransactionUpdate>,
                    response: Response<ResponseTransactionUpdate>
                ) {
                    view.onLoadingAccount(false)
                    if (response.isSuccessful) {
                        val responseTransactionUpdate: ResponseTransactionUpdate? = response.body()
                        view.onResultTransactionProof(responseTransactionUpdate!!)
                    }
                }

                override fun onFailure(call: Call<ResponseTransactionUpdate>, t: Throwable) {
                    view.onLoadingAccount(false)
                }
            })
    }
}