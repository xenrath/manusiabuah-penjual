package com.xenrath.manusiabuah.ui.delivery

import com.xenrath.manusiabuah.data.model.address.ResponseAddressDetail
import com.xenrath.manusiabuah.data.model.bargain.ResponseBargainDetail
import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionDetail
import com.xenrath.manusiabuah.data.model.rajaongkir.cost.DataCosts
import com.xenrath.manusiabuah.data.model.rajaongkir.cost.ResponseRajaongkirCost

interface DeliveryContract {

    interface Presenter {
        fun getBargain(id: Long)
        fun getAddress(user_id: String)
        fun getCost(
            key: String,
            origin: String,
            destination: String,
            weight: Int,
            courier: String
        )
        fun checkout(
            bargain_id: String,
            address_id: String,
            courier: String,
            delivery_service: String,
            total_transfer: Int,
            status: String
        )
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onLoadingAddress(loading: Boolean)
        fun onLoadingCost(loading: Boolean)
        fun onResultBargain(responseBargainDetail: ResponseBargainDetail)
        fun onResultAddress(responseAddressDetail: ResponseAddressDetail)
        fun showSpinnerCourier()
        fun getCost(courier: String)
        fun onResultCost(responseRajaongkirCost: ResponseRajaongkirCost)
        fun showSpinnerCost(courier: String, costs: List<DataCosts>)
        fun setCost(costs: DataCosts)
        fun setTotal(value: Int)
        fun onResultCheckout(responseTransactionDetail: ResponseTransactionDetail)
        fun showMessage(message: String)
    }

}