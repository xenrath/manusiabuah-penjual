package com.xenrath.manusiabuah.ui.notification

import com.xenrath.manusiabuah.data.model.offer.ResponseOfferList
import com.xenrath.manusiabuah.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationPresenter(val view: NotificationContract.View) : NotificationContract.Presenter {
    override fun offerAccepted(id: Long) {
        view.onLoading(true)
        ApiService.endPoint.offerAccepted(id).enqueue(object: Callback<ResponseOfferList> {
            override fun onResponse(
                call: Call<ResponseOfferList>,
                response: Response<ResponseOfferList>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseOfferList: ResponseOfferList? = response.body()
                    view.onResultMyBargain(responseOfferList!!)
                }
            }

            override fun onFailure(call: Call<ResponseOfferList>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun offerWaitingManage(id: Long) {
        view.onLoading(true)
        ApiService.endPoint.offerWaitingManage(id).enqueue(object :
            Callback<ResponseOfferList> {
            override fun onResponse(
                call: Call<ResponseOfferList>,
                response: Response<ResponseOfferList>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseOfferList: ResponseOfferList? = response.body()
                    view.onResultManageBargain(responseOfferList!!)
                }
            }

            override fun onFailure(call: Call<ResponseOfferList>, t: Throwable) {
                view.onLoading(false)
            }
        })
    }
}