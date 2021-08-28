package com.xenrath.manusiabuah.ui.manage.bargain.tabs.history

import com.xenrath.manusiabuah.data.model.bargain.ResponseBargainList
import com.xenrath.manusiabuah.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManageBargainHistoryPresenter(val view: ManageBargainHistoryContract.View): ManageBargainHistoryContract.Presenter {
    override fun bargainSellerHistory(user_id: String) {
        view.onLoading(true, "Menampilkan tawaran...")
        ApiService.endPoint.bargainHistoryWaiting(user_id).enqueue(object : Callback<ResponseBargainList> {
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