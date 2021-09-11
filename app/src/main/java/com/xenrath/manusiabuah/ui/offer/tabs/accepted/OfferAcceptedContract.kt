package com.xenrath.manusiabuah.ui.offer.tabs.accepted

import com.xenrath.manusiabuah.data.model.offer.ResponseOfferList

interface OfferAcceptedContract {
    interface Presenter {
        fun offerAccepted(id: Long)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onResult(responseOfferList: ResponseOfferList)
    }
}