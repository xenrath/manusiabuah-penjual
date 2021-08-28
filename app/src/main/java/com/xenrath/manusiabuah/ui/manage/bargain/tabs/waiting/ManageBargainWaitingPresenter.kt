package com.xenrath.manusiabuah.ui.manage.bargain.tabs.waiting

import com.xenrath.manusiabuah.data.model.bargain.ResponseBargainList
import com.xenrath.manusiabuah.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManageBargainWaitingPresenter(val view: ManageBargainWaitingContract.View): ManageBargainWaitingContract.Presenter {

    override fun bargainSellerWaiting(user_id: String) {
        view.onLoading(true, "Menampilkan tawaran...")
        ApiService.endPoint.bargainSellerWaiting(user_id).enqueue(object : Callback<ResponseBargainList> {
            override fun onResponse(
                call: Call<ResponseBargainList>,
                response: Response<ResponseBargainList>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseBargainList: ResponseBargainList? = response.body()
                    view.onResult(responseBargainList!!)
                }
            }

            override fun onFailure(call: Call<ResponseBargainList>, t: Throwable) {
                view.onLoading(false)
            }
        })
    }

}