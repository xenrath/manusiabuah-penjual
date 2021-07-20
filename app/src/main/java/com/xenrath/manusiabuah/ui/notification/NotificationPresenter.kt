package com.xenrath.manusiabuah.ui.notification

import com.xenrath.manusiabuah.data.database.model.ResponseBargainList
import com.xenrath.manusiabuah.data.database.model.ResponseBargainUpdate
import com.xenrath.manusiabuah.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationPresenter(val view: NotificationContract.View): NotificationContract.Presenter {

    override fun getOfferSend() {

    }

    override fun getOfferWaiting(id: String) {
        view.onLoading(true)
        ApiService.endPoint.getOfferWaiting(id).enqueue(object : Callback<ResponseBargainList> {
            override fun onResponse(
                call: Call<ResponseBargainList>,
                response: Response<ResponseBargainList>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseBargainList: ResponseBargainList? = response.body()
                    view.onResultListWaiting(responseBargainList!!)
                }
            }

            override fun onFailure(call: Call<ResponseBargainList>, t: Throwable) {
                view.onLoading(false)
            }
        })
    }

    override fun getOfferHistory(id: String) {
        view.onLoading(true)
        ApiService.endPoint.offerHistory(id).enqueue(object : Callback<ResponseBargainList>{
            override fun onResponse(
                call: Call<ResponseBargainList>,
                response: Response<ResponseBargainList>
            ) {
                view.onLoading(false)
                val responseBargainList: ResponseBargainList? = response.body()
                view.onResultListHistory(responseBargainList!!)
            }

            override fun onFailure(call: Call<ResponseBargainList>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun offerReject(id: Long) {
        view.onLoading(true)
        ApiService.endPoint.offerReject(id).enqueue(object : Callback<ResponseBargainUpdate>{
            override fun onResponse(
                call: Call<ResponseBargainUpdate>,
                response: Response<ResponseBargainUpdate>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseBargainUpdate: ResponseBargainUpdate? = response.body()
                    view.onResultUpdate(responseBargainUpdate!!)
                }
            }

            override fun onFailure(call: Call<ResponseBargainUpdate>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun offerAccept(id: Long) {
        view.onLoading(true)
        ApiService.endPoint.offerAccept(id).enqueue(object : Callback<ResponseBargainUpdate>{
            override fun onResponse(
                call: Call<ResponseBargainUpdate>,
                response: Response<ResponseBargainUpdate>
            ) {
                view.onLoading(false)
                val responseBargainUpdate: ResponseBargainUpdate? = response.body()
                view.onResultUpdate(responseBargainUpdate!!)
            }

            override fun onFailure(call: Call<ResponseBargainUpdate>, t: Throwable) {
                view.onLoading(false)
            }
        })
    }
}