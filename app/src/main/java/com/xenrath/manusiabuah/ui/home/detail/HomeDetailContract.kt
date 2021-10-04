package com.xenrath.manusiabuah.ui.home.detail

import android.widget.EditText
import com.xenrath.manusiabuah.data.model.comment.ResponseCommentList
import com.xenrath.manusiabuah.data.model.product.DataProduct
import com.xenrath.manusiabuah.data.model.product.ResponseProductDetail
import com.xenrath.manusiabuah.data.model.offer.ResponseOfferUpdate

interface HomeDetailContract {

    interface Presenter {
        fun productDetail(id: Long)
        fun offerCreate(
            user_id: String,
            product_id: String,
            price: String,
            price_offer: String,
            total_item: String,
            status: String
        )
        fun commentList(id: Long)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoadingDetail(loading: Boolean, message: String? = "Loading...")
        fun onLoadingComment(loading: Boolean)
        fun onResultDetail(responseProductDetail: ResponseProductDetail)
        fun onResultComment(responseCommentList: ResponseCommentList)
        fun onResultOffer(responseOfferUpdate: ResponseOfferUpdate)
        fun showDialogBuy(dataProduct: DataProduct)
        fun showDialogOffer(dataProduct: DataProduct)
        fun showDialogLocation(dataProduct: DataProduct)
        fun showSuccess(message: String)
        fun showError(message: String)
        fun showAlert(content: String, dataProduct: DataProduct, priceOffer: String)
        fun validationError(editText: EditText, message: String)
    }

}