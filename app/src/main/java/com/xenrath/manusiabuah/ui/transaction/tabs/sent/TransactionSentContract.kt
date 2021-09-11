package com.xenrath.manusiabuah.ui.transaction.tabs.sent

import com.xenrath.manusiabuah.data.model.offer.ResponseOfferList

interface TransactionSentContract {

    interface Presenter {
        fun getBargainWaiting(
            user_id: String,
            status: String
        )
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onResult(responseOfferList: ResponseOfferList)
    }

}