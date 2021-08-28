package com.xenrath.manusiabuah.ui.bargain.tabs.accepted

import com.xenrath.manusiabuah.data.model.bargain.ResponseBargainList
import com.xenrath.manusiabuah.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyBargainAcceptedPresenter(val view: MyBargainAcceptedContract.View): MyBargainAcceptedContract.Presenter {

    override fun getBargainAccepted(
        user_id: String, status: String
    ) {
        view.onLoading(true, "Menampilkan tawaran")
        ApiService.endPoint.getMyBargain(user_id, status).enqueue(object: Callback<ResponseBargainList> {
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