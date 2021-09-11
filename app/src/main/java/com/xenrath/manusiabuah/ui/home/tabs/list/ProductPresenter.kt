package com.xenrath.manusiabuah.ui.home.tabs.list

import com.xenrath.manusiabuah.data.model.account.ResponseAccountList
import com.xenrath.manusiabuah.data.model.product.ResponseProductList
import com.xenrath.manusiabuah.data.model.product.ResponseProductUpdate
import com.xenrath.manusiabuah.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductPresenter(val view: ProductContract.View) : ProductContract.Presenter {
    override fun productList(id: Long) {
        view.onLoading(true, "Menampilkan produk...")
        ApiService.endPoint.productList(id).enqueue(object : Callback<ResponseProductList> {
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

    override fun deleteProduct(id: Long) {
        view.onLoading(true, "Menghapus produk...")
        ApiService.endPoint.productDelete(id).enqueue(object : Callback<ResponseProductUpdate> {
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

    override fun accountList(id: Long) {
        view.onLoading(true, "Mengecek data...")
        ApiService.endPoint.accountList(id).enqueue(object : Callback<ResponseAccountList>{
            override fun onResponse(
                call: Call<ResponseAccountList>,
                response: Response<ResponseAccountList>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseAccountList: ResponseAccountList? = response.body()
                    view.onResultAccount(responseAccountList!!)
                }
            }

            override fun onFailure(call: Call<ResponseAccountList>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }
}