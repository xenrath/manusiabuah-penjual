package com.xenrath.manusiabuah.ui.product.detail

import com.xenrath.manusiabuah.data.database.model.ResponseBargain
import com.xenrath.manusiabuah.data.database.model.ResponseProductDetail
import com.xenrath.manusiabuah.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailProductPresenter(val view: DetailProductContract.View): DetailProductContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
    }

    override fun getDetail(id: Long) {
        view.onLoadingDetail(true)
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
        ).enqueue(object : Callback<ResponseBargain> {
            override fun onResponse(
                call: Call<ResponseBargain>,
                response: Response<ResponseBargain>
            ) {
                view.onLoadingBottomSheet(false)
                if (response.isSuccessful) {
                    val responseBargain: ResponseBargain? = response.body()
                    view.onResultBargain(responseBargain!!)
                }
            }

            override fun onFailure(call: Call<ResponseBargain>, t: Throwable) {
                view.onLoadingBottomSheet(false)
            }

        })
    }
}