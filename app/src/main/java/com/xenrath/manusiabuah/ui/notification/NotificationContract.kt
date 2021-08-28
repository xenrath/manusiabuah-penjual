package com.xenrath.manusiabuah.ui.notification

import com.xenrath.manusiabuah.data.model.bargain.ResponseBargainList

interface NotificationContract {
    interface Presenter {
        fun bargainAccepted(user_id: String, status: String)
        fun bargainSellerWaiting(user_id: String)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onResultMyBargain(responseBargainList: ResponseBargainList)
        fun onResultManageBargain(responseBargainList: ResponseBargainList)
        fun onResultMyPurchase(responseBargainList: ResponseBargainList)
        fun onResultManagePurchase(responseBargainList: ResponseBargainList)
    }
}