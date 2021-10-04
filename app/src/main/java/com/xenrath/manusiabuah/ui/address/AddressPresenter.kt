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

    override fun addressList(user_id: String) {
        view.onLoading(true)
        ApiService.endPoint.addressList(user_id).enqueue(object: Callback<ResponseAddressList>{
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

    override fun addressNonActive(user_id: Long) {
        view.onLoading(true)
        ApiService.endPoint.addressNonActived(user_id, "PUT").enqueue(object: Callback<ResponseAddressUpdate> {
            override fun onResponse(
                call: Call<ResponseAddressUpdate>,
                response: Response<ResponseAddressUpdate>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseAddressUpdate: ResponseAddressUpdate? = response.body()
                    view.onResultNonActive(responseAddressUpdate!!)
                }
            }

            override fun onFailure(call: Call<ResponseAddressUpdate>, t: Throwable) {
                view.onLoading(false)
            }
        })
    }

    override fun addressActive(id: Long) {
        view.onLoading(true, "Mengaktifkan alamat...")
        ApiService.endPoint.addressActived(id, "PUT").enqueue(object: Callback<ResponseAddressUpdate> {
            override fun onResponse(
                call: Call<ResponseAddressUpdate>,
                response: Response<ResponseAddressUpdate>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseAddressUpdate: ResponseAddressUpdate? = response.body()
                    view.onResultActive(responseAddressUpdate!!)
                }
            }

            override fun onFailure(call: Call<ResponseAddressUpdate>, t: Throwable) {
                view.onLoading(false)
            }
        })
    }

    override fun addressDelete(id: Long) {
        view.onLoading(true, "Menghapus alamat...")
        ApiService.endPoint.addressDelete(id).enqueue(object: Callback<ResponseAddressUpdate> {
            override fun onResponse(
                call: Call<ResponseAddressUpdate>,
                response: Response<ResponseAddressUpdate>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseAddressUpdate: ResponseAddressUpdate? = response.body()
                    view.onResultDelete(responseAddressUpdate!!)
                }
            }

            override fun onFailure(call: Call<ResponseAddressUpdate>, t: Throwable) {
                view.onLoading(false)
            }
        })
    }
}