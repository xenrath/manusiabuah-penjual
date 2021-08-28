package com.xenrath.manusiabuah.ui.delivery

import com.xenrath.manusiabuah.data.model.address.ResponseAddressDetail
import com.xenrath.manusiabuah.data.model.bargain.ResponseBargainDetail
import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionDetail
import com.xenrath.manusiabuah.data.model.rajaongkir.cost.ResponseRajaongkirCost
import com.xenrath.manusiabuah.network.ApiService
import com.xenrath.manusiabuah.network.ApiServiceRajaOngkir
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeliveryPresenter(val view: DeliveryContract.View): DeliveryContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
        view.onLoading(false)
    }

    override fun getBargain(id: Long) {
        ApiService.endPoint.getBargainDetailDelivery(id).enqueue(object: Callback<ResponseBargainDetail> {
            override fun onResponse(
                call: Call<ResponseBargainDetail>,
                response: Response<ResponseBargainDetail>
            ) {
                if (response.isSuccessful) {
                    val responseBargainDetail: ResponseBargainDetail? = response.body()
                    view.onResultBargain(responseBargainDetail!!)
                }
            }

            override fun onFailure(call: Call<ResponseBargainDetail>, t: Throwable) {

            }

        })
    }

    override fun getAddress(user_id: String) {
        view.onLoadingAddress(true)
        ApiService.endPoint.addressChecked(user_id).enqueue(object : Callback<ResponseAddressDetail>{
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
        ).enqueue(object: Callback<ResponseRajaongkirCost> {
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

    override fun checkout(
        bargain_id: String,
        address_id: String,
        courier: String,
        delivery_service: String,
        total_transfer: Int,
        status: String
    ) {
        view.onLoading(true)
        ApiService.endPoint.checkout(
            bargain_id,
            address_id,
            courier,
            delivery_service,
            total_transfer,
            status
        ).enqueue(object : Callback<ResponseTransactionDetail> {
            override fun onResponse(
                call: Call<ResponseTransactionDetail>,
                responseDetail: Response<ResponseTransactionDetail>
            ) {
                view.onLoading(false)
                if (responseDetail.isSuccessful) {
                    val responseTransactionDetail: ResponseTransactionDetail? = responseDetail.body()
                    view.onResultCheckout(responseTransactionDetail!!)
                }
            }

            override fun onFailure(call: Call<ResponseTransactionDetail>, t: Throwable) {
                view.onLoading(false)
            }
        })
    }

}