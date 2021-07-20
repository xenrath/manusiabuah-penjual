package com.xenrath.manusiabuah.ui.product.detail

import com.xenrath.manusiabuah.data.database.model.DataProduct
import com.xenrath.manusiabuah.data.database.model.ResponseBargain
import com.xenrath.manusiabuah.data.database.model.ResponseProductDetail

interface DetailProductContract {

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
        fun onLoadingDetail(loading: Boolean)
        fun onLoadingBottomSheet(loading: Boolean)
        fun onResultDetail(responseProductDetail: ResponseProductDetail)
        fun onResultBargain(responseBargain: ResponseBargain)
        fun showDialogBuy(dataProduct: DataProduct)
        fun showDialogBargain(dataProduct: DataProduct)
        fun showDialogLocation(dataProduct: DataProduct)
        fun showMessage(message: String)
    }

}