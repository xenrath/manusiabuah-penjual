package com.xenrath.manusiabuah.ui.account.update

import com.xenrath.manusiabuah.data.model.account.ResponseAccountDetail
import com.xenrath.manusiabuah.data.model.account.ResponseAccountUpdate
import com.xenrath.manusiabuah.data.model.bank.ResponseBankList
import com.xenrath.manusiabuah.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountUpdatePresenter(val view: AccountUpdateContract.View) :
    AccountUpdateContract.Presenter {
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

    override fun accountDetail(id: Long) {
        view.onLoading(true, "Menampilkan data rekening...")
        ApiService.endPoint.accountDetail(id).enqueue(object : Callback<ResponseAccountDetail> {
            override fun onResponse(
                call: Call<ResponseAccountDetail>,
                response: Response<ResponseAccountDetail>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseAccountDetail: ResponseAccountDetail? = response.body()
                    view.onResultDetail(responseAccountDetail!!)
                }
            }

            override fun onFailure(call: Call<ResponseAccountDetail>, t: Throwable) {
                view.onLoading(false)
            }
        })
    }

    override fun accountUpdate(
        id: Long,
        user_id: String,
        bank_id: String,
        bank_name: String,
        name: String,
        number: String
    ) {
        view.onLoading(true, "Menambahkan rekening...")
        ApiService.endPoint.accountUpdate(id, user_id, bank_id, bank_name, name, number, "PUT")
            .enqueue(object : Callback<ResponseAccountUpdate> {
                override fun onResponse(
                    call: Call<ResponseAccountUpdate>,
                    response: Response<ResponseAccountUpdate>
                ) {
                    view.onLoading(false)
                    if (response.isSuccessful) {
                        val responseAccountUpdate: ResponseAccountUpdate? = response.body()
                        view.onResultUpdate(responseAccountUpdate!!)
                    }
                }

                override fun onFailure(call: Call<ResponseAccountUpdate>, t: Throwable) {
                    view.onLoading(false)
                }
            })
    }
}