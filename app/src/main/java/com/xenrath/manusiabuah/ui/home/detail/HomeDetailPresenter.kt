package com.xenrath.manusiabuah.ui.home.detail

import com.xenrath.manusiabuah.data.model.product.ResponseProductDetail
import com.xenrath.manusiabuah.data.model.bargain.ResponseBargainUpdate
import com.xenrath.manusiabuah.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeDetailPresenter(val view: HomeDetailContract.View): HomeDetailContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
    }

    override fun getDetail(id: Long) {
        view.onLoadingDetail(true, "Menampilkan detail produk...")
        ApiService.endPoint.getProductDetail(id).enqueue(object : Callback<ResponseProductDetail> {
            override fun onResponse(
                call: Call<ResponseProductDetail>,
                response: Response<ResponseProductDetail>
            ) {
                view.onLoadingDetail(false)
                if (response.isSuccessful) {
                    val responseProductDetail: ResponseProductDetail? = response.body()
                    view.onResultDetail(responseProductDetail!!)
                }
            }

            override fun onFailure(call: Call<ResponseProductDetail>, t: Throwable) {
                view.onLoadingDetail(false)
            }

        })
    }

    override fun bargainProduct(
        user_id: String,
        product_id: String,
        price: String,
        price_offer: String,
        total_item: String,
        status: String
    ) {
        view.onLoadingBottomSheet(true)
        ApiService.endPoint.offerPrice(
            user_id,
            product_id,
            price,
            price_offer,
            total_item,
            status
        ).enqueue(object : Callback<ResponseBargainUpdate> {
            override fun onResponse(
                call: Call<ResponseBargainUpdate>,
                response: Response<ResponseBargainUpdate>
            ) {
                view.onLoadingBottomSheet(false)
                if (response.isSuccessful) {
                    val responseBargainUpdate: ResponseBargainUpdate? = response.body()
                    view.onResultBargain(responseBargainUpdate!!)
                }
            }

            override fun onFailure(call: Call<ResponseBargainUpdate>, t: Throwable) {
                view.onLoadingBottomSheet(false)
            }

        })
    }
}