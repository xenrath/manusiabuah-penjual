package com.xenrath.manusiabuah.ui.order

import com.xenrath.manusiabuah.data.model.address.ResponseAddressDetail
import com.xenrath.manusiabuah.data.model.offer.ResponseOfferDetail
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
        view.onLoading(false)
    }

    override fun getBargain(id: Long) {
        ApiService.endPoint.getBargainDetailDelivery(id)
            .enqueue(object : Callback<ResponseOfferDetail> {
                override fun onResponse(
                    call: Call<ResponseOfferDetail>,
                    response: Response<ResponseOfferDetail>
                ) {
                    if (response.isSuccessful) {
                        val responseOfferDetail: ResponseOfferDetail? = response.body()
                        view.onResultBargain(responseOfferDetail!!)
                    }
                }

                override fun onFailure(call: Call<ResponseOfferDetail>, t: Throwable) {

                }

            })
    }

    override fun getAddress(user_id: String) {
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
        product_id: String,
        address_id: String,
        price: String,
        total_item: String,
        courier: String,
        service_type: String,
        cost: String,
        note: String,
        total_price: String
    ) {
        view.onLoading(true)
        ApiService.endPoint.transactionOrder(
            product_id,
            address_id,
            price,
            total_item,
            courier,
            service_type,
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