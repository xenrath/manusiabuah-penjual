package com.xenrath.manusiabuah.ui.home.tabs.seller

import com.xenrath.manusiabuah.data.model.product.ResponseProductList
import com.xenrath.manusiabuah.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OtherPresenter(val view: OtherContract.View): OtherContract.Presenter {

    override fun getProduct(user_id: String, category: String) {
        view.onLoading(true, "Menampilkan produk...")
        ApiService.endPoint.productSeller(user_id, category).enqueue(object : Callback<ResponseProductList> {
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
        view.onLoading(true, "Mencari produk...")
        ApiService.endPoint.productSearch(keyword).enqueue(object : Callback<ResponseProductList> {
            override fun onResponse(
                call: Call<ResponseProductList>,
                response: Response<ResponseProductList>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseProductList: ResponseProductList? = response.body()
                    view.onResultSearch(responseProductList!!)
                }
            }

            override fun onFailure(call: Call<ResponseProductList>, t: Throwable) {
                view.onLoading(false)
            }
        })
    }
}