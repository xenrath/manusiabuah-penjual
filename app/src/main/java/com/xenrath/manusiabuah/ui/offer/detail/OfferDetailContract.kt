package com.xenrath.manusiabuah.ui.offer.detail

import com.xenrath.manusiabuah.data.model.offer.ResponseOfferDetail
import com.xenrath.manusiabuah.data.model.offer.ResponseOfferUpdate

interface OfferDetailContract {
    interface Presenter {
        fun offerDetail(id: Long)
        fun offerCanceled(offer_id: Long)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onResultDetail(responseOfferDetail: ResponseOfferDetail)
        fun onResultUpdate(responseOfferUpdate: ResponseOfferUpdate)
        fun showSuccess(message: String)
        fun showError(message: String)
        fun showAlert()
    }
}