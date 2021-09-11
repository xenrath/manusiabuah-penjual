package com.xenrath.manusiabuah.ui.address.add

import com.xenrath.manusiabuah.data.model.address.ResponseAddressUpdate
import com.xenrath.manusiabuah.data.model.rajaongkir.territory.ResponseRajaongkirTerritory
import com.xenrath.manusiabuah.network.ApiService
import com.xenrath.manusiabuah.network.ApiServiceRajaOngkir
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddAddressPresenter(val view: AddAddressContract.View): AddAddressContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
        view.onLoading(false)
    }

    override fun getProvince(key: String) {
        view.onLoadingTerritory(true)
        ApiServiceRajaOngkir.endPoint.getProvince(key).enqueue(object : Callback<ResponseRajaongkirTerritory> {
            override fun onResponse(
                call: Call<ResponseRajaongkirTerritory>,
                response: Response<ResponseRajaongkirTerritory>
            ) {
                view.onLoadingTerritory(false)
                if (response.isSuccessful) {
                    val responseRajaOngkirTerritory: ResponseRajaongkirTerritory? = response.body()
                    view.onResultProvince(responseRajaOngkirTerritory!!)
                }
            }

            override fun onFailure(call: Call<ResponseRajaongkirTerritory>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun getCity(key: String, id: String) {
        view.onLoadingTerritory(true)
        ApiServiceRajaOngkir.endPoint.getCity(key, id).enqueue(object : Callback<ResponseRajaongkirTerritory> {
            override fun onResponse(
                call: Call<ResponseRajaongkirTerritory>,
                response: Response<ResponseRajaongkirTerritory>
            ) {
                view.onLoadingTerritory(false)
                if (response.isSuccessful) {
                    val responseRajaOngkirTerritory: ResponseRajaongkirTerritory? = response.body()
                    view.onResultCity(responseRajaOngkirTerritory!!)
                }
            }

            override fun onFailure(call: Call<ResponseRajaongkirTerritory>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun insertAddress(
        user_id: String,
        name: String,
        phone: String,
        address: String,
        place: String,
        province_id: String,
        province_name: String,
        city_id: String,
        city_name: String,
        postal_code: String,
    ) {
        view.onLoading(true)
        ApiService.endPoint.addressCreate(
            user_id,
            name,
            phone,
            address,
            place,
            province_id,
            province_name,
            city_id,
            city_name,
            postal_code,
        ).enqueue(object : Callback<ResponseAddressUpdate> {
            override fun onResponse(
                call: Call<ResponseAddressUpdate>,
                response: Response<ResponseAddressUpdate>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseAddressUpdate: ResponseAddressUpdate? = response.body()
                    view.onResult(responseAddressUpdate!!)
                }
            }

            override fun onFailure(call: Call<ResponseAddressUpdate>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

}