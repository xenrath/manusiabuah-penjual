package com.xenrath.manusiabuah.ui.notification

import com.xenrath.manusiabuah.data.model.offer.ResponseOfferList

interface NotificationContract {
    interface Presenter {
        fun offerAccepted(id: Long)
        fun offerWaitingManage(id: Long)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onResultMyBargain(responseOfferList: ResponseOfferList)
        fun onResultManageBargain(responseOfferList: ResponseOfferList)
        fun onResultMyPurchase(responseOfferList: ResponseOfferList)
        fun onResultManagePurchase(responseOfferList: ResponseOfferList)
    }
}