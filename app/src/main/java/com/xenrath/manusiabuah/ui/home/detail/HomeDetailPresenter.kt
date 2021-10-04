package com.xenrath.manusiabuah.ui.home.detail

import com.xenrath.manusiabuah.data.model.comment.ResponseCommentList
import com.xenrath.manusiabuah.data.model.product.ResponseProductDetail
import com.xenrath.manusiabuah.data.model.offer.ResponseOfferUpdate
import com.xenrath.manusiabuah.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeDetailPresenter(val view: HomeDetailContract.View): HomeDetailContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
    }

    override fun productDetail(id: Long) {
        view.onLoadingDetail(true, "Menampilkan detail produk...")
        ApiService.endPoint.productDetail(id).enqueue(object : Callback<ResponseProductDetail> {
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

    override fun offerCreate(
        user_id: String,
        product_id: String,
        price: String,
        price_offer: String,
        total_item: String,
        status: String
    ) {
        view.onLoadingDetail(true)
        ApiService.endPoint.offerPrice(
            user_id,
            product_id,
            price,
            price_offer,
            total_item,
            status
        ).enqueue(object : Callback<ResponseOfferUpdate> {
            override fun onResponse(
                call: Call<ResponseOfferUpdate>,
                response: Response<ResponseOfferUpdate>
            ) {
                view.onLoadingDetail(false)
                if (response.isSuccessful) {
                    val responseOfferUpdate: ResponseOfferUpdate? = response.body()
                    view.onResultOffer(responseOfferUpdate!!)
                }
            }

            override fun onFailure(call: Call<ResponseOfferUpdate>, t: Throwable) {
                view.onLoadingDetail(false)
            }

        })
    }

    override fun commentList(id: Long) {
        view.onLoadingComment(true)
        ApiService.endPoint.commentList(id).enqueue(object : Callback<ResponseCommentList>{
            override fun onResponse(
                call: Call<ResponseCommentList>,
                response: Response<ResponseCommentList>
            ) {
                view.onLoadingDetail(false)
                if (response.isSuccessful) {
                    val responseCommentList: ResponseCommentList? = response.body()
                    view.onResultComment(responseCommentList!!)
                }
            }

            override fun onFailure(call: Call<ResponseCommentList>, t: Throwable) {
                view.onLoadingComment(false)
            }
        })
    }
}