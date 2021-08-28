package com.xenrath.manusiabuah.ui.bargain.detail

import com.xenrath.manusiabuah.data.model.bargain.ResponseBargainDetail
import com.xenrath.manusiabuah.data.model.bargain.ResponseBargainUpdate
import com.xenrath.manusiabuah.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyBargainDetailPresenter(val view: MyBargainDetailContract.View): MyBargainDetailContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
    }

    override fun getBargainDetail(id: Long) {
        view.onLoadingGet(true)
        ApiService.endPoint.getBargainDetail(id).enqueue(object : Callback<ResponseBargainDetail> {
            override fun onResponse(
                call: Call<ResponseBargainDetail>,
                response: Response<ResponseBargainDetail>
            ) {
                view.onLoadingGet(false)
                val responseBargainDetail: ResponseBargainDetail? = response.body()
                view.onResult(responseBargainDetail!!)
            }

            override fun onFailure(call: Call<ResponseBargainDetail>, t: Throwable) {
                view.onLoadingGet(false)
            }

        })
    }

    override fun updateBargainStatus(id: Long, status: String) {
        view.onLoadingAction(true)
        ApiService.endPoint.getBargainAction(
            id,
            status,
            "PUT"
        ).enqueue(object: Callback<ResponseBargainUpdate> {
            override fun onResponse(
                call: Call<ResponseBargainUpdate>,
                response: Response<ResponseBargainUpdate>
            ) {
                view.onLoadingAction(false)
                if (response.isSuccessful){
                    val responseBargainUpdate: ResponseBargainUpdate? = response.body()
                    view.onResultUpdate(responseBargainUpdate!!)
                }
            }

            override fun onFailure(call: Call<ResponseBargainUpdate>, t: Throwable) {
                view.onLoadingAction(false)
            }

        })
    }


}