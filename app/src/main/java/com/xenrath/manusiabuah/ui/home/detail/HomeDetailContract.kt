package com.xenrath.manusiabuah.ui.home.detail

import com.xenrath.manusiabuah.data.model.product.DataProduct
import com.xenrath.manusiabuah.data.model.product.ResponseProductDetail
import com.xenrath.manusiabuah.data.model.offer.ResponseOfferUpdate

interface HomeDetailContract {

    interface Presenter {
        fun getDetail(id: Long)
        fun bargainProduct(
            user_id: String,
            product_id: String,
            price: String,
            price_offer: String,
            total_item: String,
            status: String
        )
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoadingDetail(loading: Boolean, message: String? = "Loading...")
        fun onLoadingBottomSheet(loading: Boolean)
        fun onResultDetail(responseProductDetail: ResponseProductDetail)
        fun onResultBargain(responseOfferUpdate: ResponseOfferUpdate)
        fun showDialogBuy(dataProduct: DataProduct)
        fun showDialogBargain(dataProduct: DataProduct)
        fun showDialogLocation(dataProduct: DataProduct)
        fun showMessage(message: String)
    }

}