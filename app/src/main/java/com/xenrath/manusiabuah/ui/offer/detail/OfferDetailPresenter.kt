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
        view.onLoadingGet(true)
        ApiService.endPoint.offerDetail(id).enqueue(object : Callback<ResponseOfferDetail> {
            override fun onResponse(
                call: Call<ResponseOfferDetail>,
                response: Response<ResponseOfferDetail>
            ) {
                view.onLoadingGet(false)
                if (response.isSuccessful) {
                    val responseOfferDetail: ResponseOfferDetail? = response.body()
                    view.onResult(responseOfferDetail!!)
                }
            }

            override fun onFailure(call: Call<ResponseOfferDetail>, t: Throwable) {
                view.onLoadingGet(false)
            }

        })
    }

    override fun offerCanceled(id: Long) {
        view.onLoadingAction(true)
        ApiService.endPoint.offerCanceled(id, "PUT")
            .enqueue(object : Callback<ResponseOfferUpdate> {
                override fun onResponse(
                    call: Call<ResponseOfferUpdate>,
                    response: Response<ResponseOfferUpdate>
                ) {
                    view.onLoadingAction(false)
                    if (response.isSuccessful) {
                        val responseOfferUpdate: ResponseOfferUpdate? = response.body()
                        view.onResultUpdate(responseOfferUpdate!!)
                    }
                }

                override fun onFailure(call: Call<ResponseOfferUpdate>, t: Throwable) {
                    view.onLoadingAction(false)
                }

            })
    }


}