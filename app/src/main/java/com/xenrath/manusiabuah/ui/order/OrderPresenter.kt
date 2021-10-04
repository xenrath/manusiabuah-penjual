package com.xenrath.manusiabuah.ui.order

import com.xenrath.manusiabuah.data.model.address.ResponseAddressDetail
import com.xenrath.manusiabuah.data.model.offer.ResponseOfferDetail
import com.xenrath.manusiabuah.data.model.product.ResponseProductDetail
import com.xenrath.manusiabuah.data.model.rajaongkir.cost.ResponseRajaongkirCost
import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionUpdate
import com.xenrath.manusiabuah.network.ApiService
import com.xenrath.manusiabuah.network.ApiServiceRajaOngkir
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderPresenter(val view: OrderContract.View) : OrderContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
    }

    override fun offerDetail(id: Long) {
        view.onLoadingProduct(true)
        ApiService.endPoint.offerDetail(id)
            .enqueue(object : Callback<ResponseOfferDetail> {
                override fun onResponse(
                    call: Call<ResponseOfferDetail>,
                    response: Response<ResponseOfferDetail>
                ) {
                    view.onLoadingProduct(false)
                    if (response.isSuccessful) {
                        val responseOfferDetail: ResponseOfferDetail? = response.body()
                        view.onResultOffer(responseOfferDetail!!)
                    }
                }

                override fun onFailure(call: Call<ResponseOfferDetail>, t: Throwable) {
                    view.onLoadingProduct(false)
                }
            })
    }

    override fun productDetail(id: Long) {
        view.onLoadingProduct(true)
        ApiService.endPoint.productDetail(id)
            .enqueue(object : Callback<ResponseProductDetail> {
                override fun onResponse(
                    call: Call<ResponseProductDetail>,
                    response: Response<ResponseProductDetail>
                ) {
                    view.onLoadingProduct(false)
                    if (response.isSuccessful) {
                        val responseProductDetail: ResponseProductDetail? = response.body()
                        view.onResultProduct(responseProductDetail!!)
                    }
                }

                override fun onFailure(call: Call<ResponseProductDetail>, t: Throwable) {
                    view.onLoadingProduct(false)
                }
            })
    }

    override fun addressChecked(user_id: Long) {
        view.onLoadingAddress(true)
        ApiService.endPoint.addressChecked(user_id)
            .enqueue(object : Callback<ResponseAddressDetail> {
                override fun onResponse(
                    call: Call<ResponseAddressDetail>,
                    response: Response<ResponseAddressDetail>
                ) {
                    view.onLoadingAddress(false)
                    if (response.isSuccessful) {
                        val responseAddressDetail: ResponseAddressDetail? = response.body()
                        view.onResultAddress(responseAddressDetail!!)
                    }
                }

                override fun onFailure(call: Call<ResponseAddressDetail>, t: Throwable) {
                    view.onLoadingAddress(false)
                }

            })
    }

    override fun getCost(
        key: String,
        origin: String,
        destination: String,
        weight: Int,
        courier: String
    ) {
        view.onLoadingCost(true)
        ApiServiceRajaOngkir.endPoint.calculateCost(
            key,
            origin,
            destination,
            weight,
            courier
        ).enqueue(object : Callback<ResponseRajaongkirCost> {
            override fun onResponse(
                call: Call<ResponseRajaongkirCost>,
                response: Response<ResponseRajaongkirCost>
            ) {
                view.onLoadingCost(false)
                if (response.isSuccessful) {
                    val responseRajaongkirCost: ResponseRajaongkirCost? = response.body()
                    view.onResultCost(responseRajaongkirCost!!)
                }
            }

            override fun onFailure(call: Call<ResponseRajaongkirCost>, t: Throwable) {
                view.onLoadingCost(false)
            }

        })
    }

    override fun transactionOrder(
        user_id: String,
        product_id: String,
        recipient: String,
        phone: String,
        place: String,
        origin: String,
        total_item: String,
        price: String,
        courier: String,
        service_type: String,
        estimation: String,
        cost: String,
        note: String,
        total_price: String
    ) {
        ApiService.endPoint.transactionOrder(
            user_id,
            product_id,
            recipient,
            phone,
            place,
            origin,
            total_item,
            price,
            courier,
            service_type,
            estimation,
            cost,
            note,
            total_price
        ).enqueue(object : Callback<ResponseTransactionUpdate> {
            override fun onResponse(
                call: Call<ResponseTransactionUpdate>,
                responseDetail: Response<ResponseTransactionUpdate>
            ) {
                view.onLoading(false)
                if (responseDetail.isSuccessful) {
                    val responseTransactionUpdate: ResponseTransactionUpdate? =
                        responseDetail.body()
                    view.onResultOrder(responseTransactionUpdate!!)
                }
            }

            override fun onFailure(call: Call<ResponseTransactionUpdate>, t: Throwable) {
                view.onLoading(false)
            }
        })
    }
}