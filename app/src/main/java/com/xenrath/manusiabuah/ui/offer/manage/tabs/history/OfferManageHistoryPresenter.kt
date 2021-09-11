package com.xenrath.manusiabuah.ui.offer.manage.tabs.history

import com.xenrath.manusiabuah.data.model.offer.ResponseOfferList
import com.xenrath.manusiabuah.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OfferManageHistoryPresenter(val view: OfferManageHistoryContract.View): OfferManageHistoryContract.Presenter {
    override fun offerHistoryManage(id: Long) {
        view.onLoading(true, "Menampilkan tawaran...")
        ApiService.endPoint.offerHistoryManage(id).enqueue(object : Callback<ResponseOfferList> {
            override fun onResponse(
                call: Call<ResponseOfferList>,
                response: Response<ResponseOfferList>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseOfferList: ResponseOfferList? = response.body()
                    view.onResult(responseOfferList!!)
                }
            }

            override fun onFailure(call: Call<ResponseOfferList>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }
}