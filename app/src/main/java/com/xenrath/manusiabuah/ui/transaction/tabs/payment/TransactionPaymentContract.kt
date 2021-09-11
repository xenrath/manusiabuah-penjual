package com.xenrath.manusiabuah.ui.transaction.tabs.payment

import com.xenrath.manusiabuah.data.model.offer.ResponseOfferList

interface TransactionPaymentContract {
    interface Presenter {
        fun getBargainHistory(
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