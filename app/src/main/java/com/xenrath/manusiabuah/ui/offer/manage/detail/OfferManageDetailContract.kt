package com.xenrath.manusiabuah.ui.offer.manage.detail

import com.xenrath.manusiabuah.data.model.offer.DataOffer
import com.xenrath.manusiabuah.data.model.offer.ResponseOfferDetail
import com.xenrath.manusiabuah.data.model.offer.ResponseOfferUpdate

interface OfferManageDetailContract {
    interface Presenter {
        fun offerDetail(id: Long)
        fun offerReject(id: Long)
        fun offerAccept(id: Long)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onResultDetail(responseOfferDetail: ResponseOfferDetail)
        fun onResultUpdate(responseOfferUpdate: ResponseOfferUpdate)
        fun showAlertReject(offer: DataOffer)
        fun showAlertAccept(offer: DataOffer)
    }
}