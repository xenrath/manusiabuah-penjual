package com.xenrath.manusiabuah.ui.home

import com.xenrath.manusiabuah.data.ResponseProduct
import com.xenrath.manusiabuah.data.database.model.ResponseProductList
import com.xenrath.manusiabuah.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePresenter(val view: HomeContract.View): HomeContract.Presenter {

    override fun getProduct(user_id: String, category: String) {
        view.onLoading(true)
        ApiService.endPoint.getProductForSale(user_id, category).enqueue(object : Callback<ResponseProductList> {
            override fun onResponse(
                call: Call<ResponseProductList>,
                response: Response<ResponseProductList>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseProductList: ResponseProductList? = response.body()
                    view.onResultList(responseProductList!!)
                }
            }

            override fun onFailure(call: Call<ResponseProductList>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun searchProduct(keyword: String) {
        view.onLoading(true)
        ApiService.endPoint.searchProduct(keyword).enqueue(object : Callback<ResponseProduct> {
            override fun onResponse(
                call: Call<ResponseProduct>,
                response: Response<ResponseProduct>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseProduct: ResponseProduct? = response.body()
                    view.onResultSearch(responseProduct!!)
                }
            }

            override fun onFailure(call: Call<ResponseProduct>, t: Throwable) {
                view.onLoading(false)
            }
        })
    }
}