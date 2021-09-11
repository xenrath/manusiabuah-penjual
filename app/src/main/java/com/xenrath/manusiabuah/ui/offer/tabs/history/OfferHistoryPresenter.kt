package com.xenrath.manusiabuah.ui.offer.tabs.history

import com.xenrath.manusiabuah.data.model.offer.ResponseOfferList
import com.xenrath.manusiabuah.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OfferHistoryPresenter(val view: OfferHistoryContract.View): OfferHistoryContract.Presenter {
    override fun offerHistory(id: Long) {
        view.onLoading(true, "Menampilkan tawaran...")
        ApiService.endPoint.offerHistory(id).enqueue(object: Callback<ResponseOfferList> {
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