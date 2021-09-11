package com.xenrath.manusiabuah.ui.account.create

import com.xenrath.manusiabuah.data.model.account.ResponseAccountUpdate
import com.xenrath.manusiabuah.data.model.bank.ResponseBankList
import com.xenrath.manusiabuah.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountCreatePresenter(val view: AccountCreateContract.View) :
    AccountCreateContract.Presenter {
    init {
        view.initActivity()
        view.initListener()
    }

    override fun bankList() {
        view.onLoadingBank(true)
        ApiService.endPoint.bankList().enqueue(object : Callback<ResponseBankList> {
            override fun onResponse(
                call: Call<ResponseBankList>,
                response: Response<ResponseBankList>
            ) {
                view.onLoadingBank(false)
                if (response.isSuccessful) {
                    val responseBankList: ResponseBankList? = response.body()
                    view.onResultBank(responseBankList!!)
                }
            }

            override fun onFailure(call: Call<ResponseBankList>, t: Throwable) {
                view.onLoadingBank(false)
            }

        })
    }

    override fun accountCreate(
        user_id: String,
        bank_id: String,
        bank_name: String,
        name: String,
        number: String
    ) {
        view.onLoading(true, "Menambahkan rekening...")
        ApiService.endPoint.accountCreate(user_id, bank_id, bank_name, name, number)
            .enqueue(object : Callback<ResponseAccountUpdate> {
                override fun onResponse(
                    call: Call<ResponseAccountUpdate>,
                    response: Response<ResponseAccountUpdate>
                ) {
                    view.onLoading(false)
                    if (response.isSuccessful) {
                        val responseAccountUpdate: ResponseAccountUpdate? = response.body()
                        view.onResult(responseAccountUpdate!!)
                    }
                }

                override fun onFailure(call: Call<ResponseAccountUpdate>, t: Throwable) {
                    view.onLoading(false)
                }
            })
    }
}