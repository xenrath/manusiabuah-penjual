package com.xenrath.manusiabuah.ui.product

import com.xenrath.manusiabuah.data.database.model.ResponseProductList
import com.xenrath.manusiabuah.data.database.model.ResponseProductUpdate
import com.xenrath.manusiabuah.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductPresenter(val view: ProductContract.View): ProductContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
    }

    override fun getProduct(user_id: String) {
        view.onLoading(true)
        ApiService.endPoint.myProduct(user_id).enqueue(object : Callback<ResponseProductList> {
            override fun onResponse(
                call: Call<ResponseProductList>,
                response: Response<ResponseProductList>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseProductList: ResponseProductList? = response.body()
                    view.onResult(responseProductList!!)
                }
            }

            override fun onFailure(call: Call<ResponseProductList>, t: Throwable) {
                view.onLoading(false)
            }
        })
    }

    override fun deleteProduct(id: Long) {
        view.onLoading(true)
        ApiService.endPoint.deleteProduct(id).enqueue(object : Callback<ResponseProductUpdate> {
            override fun onResponse(
                call: Call<ResponseProductUpdate>,
                response: Response<ResponseProductUpdate>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseProductUpdate: ResponseProductUpdate? = response.body()
                    view.onResultDelete(responseProductUpdate!!)
                }
            }

            override fun onFailure(call: Call<ResponseProductUpdate>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }
}