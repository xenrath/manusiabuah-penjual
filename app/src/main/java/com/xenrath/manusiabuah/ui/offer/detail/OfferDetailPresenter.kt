package com.xenrath.manusiabuah.ui.offer.detail

import com.xenrath.manusiabuah.data.model.offer.ResponseOfferDetail
import com.xenrath.manusiabuah.data.model.offer.ResponseOfferUpdate
import com.xenrath.manusiabuah.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OfferDetailPresenter(val view: OfferDetailContract.View) : OfferDetailContract.Presenter {
    init {
        view.initActivity()
        view.initListener()
    }

    override fun offerDetail(id: Long) {
        view.onLoading(true, "Menampilkan detail tawaran...")
        ApiService.endPoint.offerDetail(id).enqueue(object : Callback<ResponseOfferDetail> {
            override fun onResponse(
                call: Call<ResponseOfferDetail>,
                response: Response<ResponseOfferDetail>
            ) {
                view.onLoading(false)
                val responseOfferDetail: ResponseOfferDetail? = response.body()
                view.onResultDetail(responseOfferDetail!!)
            }

            override fun onFailure(call: Call<ResponseOfferDetail>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun offerCanceled(offer_id: Long) {
        view.onLoading(true, "Membatalkan tawaran...")
        ApiService.endPoint.offerCanceled(offer_id, "PUT")
            .enqueue(object : Callback<ResponseOfferUpdate> {
                override fun onResponse(
                    call: Call<ResponseOfferUpdate>,
                    response: Response<ResponseOfferUpdate>
                ) {
                    view.onLoading(false)
                    if (response.isSuccessful) {
                        val responseOfferUpdate: ResponseOfferUpdate? = response.body()
                        view.onResultUpdate(responseOfferUpdate!!)
                    }
                }

                override fun onFailure(call: Call<ResponseOfferUpdate>, t: Throwable) {
                    view.onLoading(false)
                }

            })
    }
}