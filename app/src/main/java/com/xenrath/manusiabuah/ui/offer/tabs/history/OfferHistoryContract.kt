package com.xenrath.manusiabuah.ui.offer.tabs.history

import com.xenrath.manusiabuah.data.model.offer.ResponseOfferList

interface OfferHistoryContract {
    interface Presenter {
        fun offerHistory(id: Long)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onResult(responseOfferList: ResponseOfferList)
    }
}