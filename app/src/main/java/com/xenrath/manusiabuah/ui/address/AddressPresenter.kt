package com.xenrath.manusiabuah.ui.address

import com.xenrath.manusiabuah.data.model.address.ResponseAddressList
import com.xenrath.manusiabuah.data.model.address.ResponseAddressUpdate
import com.xenrath.manusiabuah.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddressPresenter(val view: AddressContract.View): AddressContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
        view.onLoading(false)
    }

    override fun getAddress(user_id: String) {
        view.onLoading(true)
        ApiService.endPoint.getAddress(user_id).enqueue(object: Callback<ResponseAddressList>{
            override fun onResponse(
                call: Call<ResponseAddressList>,
                response: Response<ResponseAddressList>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseAddressList: ResponseAddressList? = response.body()
                    view.onResultList(responseAddressList!!)
                }
            }

            override fun onFailure(call: Call<ResponseAddressList>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun checkAddress(id: Long, user_id: String) {
        view.onLoading(true)
        ApiService.endPoint.checkAddress(id, user_id).enqueue(object: Callback<ResponseAddressUpdate> {
            override fun onResponse(
                call: Call<ResponseAddressUpdate>,
                response: Response<ResponseAddressUpdate>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseAddressUpdate: ResponseAddressUpdate? = response.body()
                    view.onResultChoice(responseAddressUpdate!!)
                }
            }

            override fun onFailure(call: Call<ResponseAddressUpdate>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }
}