package com.xenrath.manusiabuah.ui.order

import com.xenrath.manusiabuah.data.model.address.ResponseAddressDetail
import com.xenrath.manusiabuah.data.model.offer.ResponseOfferDetail
import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionDetail
import com.xenrath.manusiabuah.data.model.rajaongkir.cost.DataCosts
import com.xenrath.manusiabuah.data.model.rajaongkir.cost.ResponseRajaongkirCost
import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionUpdate

interface OrderContract {

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

        fun transactionOrder(
            product_id: String,
            address_id: String,
            price: String,
            total_item: String,
            courier: String,
            service_type: String,
            cost: String,
            note: String,
            total_price: String
        )
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onLoadingAddress(loading: Boolean)
        fun onLoadingCost(loading: Boolean)
        fun onResultBargain(responseOfferDetail: ResponseOfferDetail)
        fun onResultAddress(responseAddressDetail: ResponseAddressDetail)
        fun showSpinnerCourier()
        fun getCost(courier: String)
        fun onResultCost(responseRajaongkirCost: ResponseRajaongkirCost)
        fun onResultOrder(responseTransactionUpdate: ResponseTransactionUpdate)
        fun showSpinnerCost(courier: String, costs: List<DataCosts>)
        fun setCost()
        fun setTotal(value: Int)
        fun onResultCheckout(responseTransactionDetail: ResponseTransactionDetail)
        fun showSuccess(message: String)
        fun showError(message: String)
        fun showAlertOrder(message: String)
    }

}