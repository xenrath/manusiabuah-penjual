package com.xenrath.manusiabuah.ui.offer.detail

import com.xenrath.manusiabuah.data.model.offer.ResponseOfferDetail
import com.xenrath.manusiabuah.data.model.offer.ResponseOfferUpdate

interface OfferDetailContract {
    interface Presenter {
        fun offerDetail(id: Long)
        fun offerCanceled(id: Long)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoadingGet(loading: Boolean, message: String? = "Loading...")
        fun onLoadingAction(loading: Boolean)
        fun onResult(responseOfferDetail: ResponseOfferDetail)
        fun onResultUpdate(responseOfferUpdate: ResponseOfferUpdate)
        fun showMessage(message: String)
    }
}