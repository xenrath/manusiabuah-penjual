package com.xenrath.manusiabuah.ui.manage.bargain.detail

import com.xenrath.manusiabuah.data.model.bargain.ResponseBargainDetail
import com.xenrath.manusiabuah.data.model.bargain.ResponseBargainUpdate
import com.xenrath.manusiabuah.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManageBargainDetailPresenter(val view: ManageBargainDetailContract.View) :
    ManageBargainDetailContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
    }

    override fun getBargainDetail(id: Long) {
        view.onLoading(true, "Menampilkan detail tawaran...")
        ApiService.endPoint.getBargainDetail(id).enqueue(object : Callback<ResponseBargainDetail> {
            override fun onResponse(
                call: Call<ResponseBargainDetail>,
                response: Response<ResponseBargainDetail>
            ) {
                view.onLoading(false)
                val responseBargainDetail: ResponseBargainDetail? = response.body()
                view.onResultDetail(responseBargainDetail!!)
            }

            override fun onFailure(call: Call<ResponseBargainDetail>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun bargainReject(id: Long) {
        view.onLoading(true, "Menolak tawaran...")
        ApiService.endPoint.bargainReject(id).enqueue(object : Callback<ResponseBargainUpdate> {
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

    override fun bargainAccept(id: Long) {
        view.onLoading(true, "Menerima tawaran...")
        ApiService.endPoint.bargainAccept(id).enqueue(object : Callback<ResponseBargainUpdate> {
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

    override fun updateBargainStatus(id: Long, status: String) {
        view.onLoading(true)
        ApiService.endPoint.getBargainAction(
            id,
            status,
            "PUT"
        ).enqueue(object : Callback<ResponseBargainUpdate> {
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


}