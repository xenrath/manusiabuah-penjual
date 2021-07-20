package com.xenrath.manusiabuah.ui.product.create

import com.xenrath.manusiabuah.data.database.model.ResponseProductUpdate
import com.xenrath.manusiabuah.network.ApiService
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

    override fun insertProduct(
        user_id: String,
        name: String,
        category: String,
        price: String,
        description: String,
        address: String,
        latitude: String,
        longitude: String,
        image: File,
        stock: String
    ) {
        val requestBody: RequestBody = image.asRequestBody("image/*".toMediaTypeOrNull())
        val multipartBody: MultipartBody.Part =
            MultipartBody.Part.createFormData("image", image.name, requestBody)

        view.onLoading(true)
        ApiService.endPoint.insertProduct(
            user_id,
            name,
            category,
            price,
            description,
            address,
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