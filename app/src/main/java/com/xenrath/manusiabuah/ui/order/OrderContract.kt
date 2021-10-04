package com.xenrath.manusiabuah.ui.order

import com.xenrath.manusiabuah.data.model.address.ResponseAddressDetail
import com.xenrath.manusiabuah.data.model.offer.ResponseOfferDetail
import com.xenrath.manusiabuah.data.model.product.ResponseProductDetail
import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionDetail
import com.xenrath.manusiabuah.data.model.rajaongkir.cost.DataCosts
import com.xenrath.manusiabuah.data.model.rajaongkir.cost.ResponseRajaongkirCost
import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionUpdate

interface OrderContract {

    interface Presenter {
        fun offerDetail(id: Long)
        fun productDetail(id: Long)
        fun addressChecked(user_id: Long)
        fun getCost(
            key: String,
            origin: String,
            destination: String,
            weight: Int,
            courier: String
        )
        fun transactionOrder(
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
        )
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onLoadingProduct(loading: Boolean)
        fun onLoadingAddress(loading: Boolean)
        fun onLoadingCost(loading: Boolean)
        fun onResultOffer(responseOfferDetail: ResponseOfferDetail)
        fun onResultProduct(responseProductDetail: ResponseProductDetail)
        fun onResultAddress(responseAddressDetail: ResponseAddressDetail)
        fun showSpinnerCourier()
        fun onResultCost(responseRajaongkirCost: ResponseRajaongkirCost)
        fun onResultOrder(responseTransactionUpdate: ResponseTransactionUpdate)
        fun showSpinnerServiceType(costs: List<DataCosts>)
        fun setCost(serviceType: String)
        fun setTotal(value: Int)
        fun onResultCheckout(responseTransactionDetail: ResponseTransactionDetail)
        fun showSuccess(message: String)
        fun showSuccessOrder(message: String)
        fun showError(message: String)
        fun showAlertOrder(message: String)
    }

}