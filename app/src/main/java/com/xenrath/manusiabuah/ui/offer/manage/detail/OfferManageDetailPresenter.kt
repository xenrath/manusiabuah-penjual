package com.xenrath.manusiabuah.ui.offer.manage.detail

import com.xenrath.manusiabuah.data.model.offer.ResponseOfferDetail
import com.xenrath.manusiabuah.data.model.offer.ResponseOfferUpdate
import com.xenrath.manusiabuah.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OfferManageDetailPresenter(val view: OfferManageDetailContract.View) :
    OfferManageDetailContract.Presenter {

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

    override fun offerReject(id: Long) {
        view.onLoading(true, "Menolak tawaran...")
        ApiService.endPoint.offerReject(id, "PUT")
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

    override fun offerAccept(id: Long) {
        view.onLoading(true, "Menerima tawaran...")
        ApiService.endPoint.offerAccept(id, "PUT")
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