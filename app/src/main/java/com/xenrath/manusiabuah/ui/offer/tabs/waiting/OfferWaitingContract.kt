package com.xenrath.manusiabuah.ui.offer.tabs.waiting

import com.xenrath.manusiabuah.data.model.offer.ResponseOfferList

interface OfferWaitingContract {

    interface Presenter {
        fun offerWaiting(id: Long)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onResult(responseOfferList: ResponseOfferList)
    }

}