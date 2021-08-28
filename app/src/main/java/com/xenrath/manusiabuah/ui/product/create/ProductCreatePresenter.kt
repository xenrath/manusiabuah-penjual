package com.xenrath.manusiabuah.ui.product.create

import com.xenrath.manusiabuah.data.model.product.ResponseProductUpdate
import com.xenrath.manusiabuah.data.model.rajaongkir.territory.ResponseRajaongkirTerritory
import com.xenrath.manusiabuah.network.ApiService
import com.xenrath.manusiabuah.network.ApiServiceRajaOngkir
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ProductCreatePresenter(val view: ProductCreateContract.View): ProductCreateContract.Presenter {

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
                responseRajaongkir: Response<ResponseRajaongkirTerritory>
            ) {
                view.onLoadingTerritory(false)
                if (responseRajaongkir.isSuccessful) {
                    val responseRajaongkirTerritory: ResponseRajaongkirTerritory? = responseRajaongkir.body()
                    view.onResultProvince(responseRajaongkirTerritory!!)
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
                responseRajaongkir: Response<ResponseRajaongkirTerritory>
            ) {
                view.onLoadingTerritory(false)
                if (responseRajaongkir.isSuccessful) {
                    val responseRajaongkirTerritory: ResponseRajaongkirTerritory? = responseRajaongkir.body()
                    view.onResultCity(responseRajaongkirTerritory!!)
                }
            }

            override fun onFailure(call: Call<ResponseRajaongkirTerritory>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun insertProduct(
        user_id: String,
        name: String,
        category: String,
        price: String,
        description: String,
        address: String,
        province_id: String,
        province_name: String,
        city_id: String,
        city_name: String,
        postal_code: String,
        latitude: String,
        longitude: String,
        image: File,
        stock: String
    ) {
        val requestBody: RequestBody = image.asRequestBody("image/*".toMediaTypeOrNull())
        val multipartBody: MultipartBody.Part =
            MultipartBody.Part.createFormData("image", image.name, requestBody)

        view.onLoading(true, "Menambahkan produk...")
        ApiService.endPoint.insertProduct(
            user_id,
            name,
            category,
            price,
            description,
            address,
            province_id,
            province_name,
            city_id,
            city_name,
            postal_code,
            latitude,
            longitude,
            multipartBody,
            stock,
        )
            .enqueue(object : Callback<ResponseProductUpdate> {
                override fun onResponse(
                    call: Call<ResponseProductUpdate>,
                    response: Response<ResponseProductUpdate>
                ) {
                    view.onLoading(false)
                    if (response.isSuccessful) {
                        val responseProductUpdate: ResponseProductUpdate? = response.body()
                        view.onResult(responseProductUpdate!!)
                    }
                }

                override fun onFailure(call: Call<ResponseProductUpdate>, t: Throwable) {
                    view.onLoading(false)
                }
            })
    }
}