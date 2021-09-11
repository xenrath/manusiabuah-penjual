package com.xenrath.manusiabuah.ui.offer.manage.tabs.history

import com.xenrath.manusiabuah.data.model.offer.ResponseOfferList

interface OfferManageHistoryContract {
    interface Presenter {
        fun offerHistoryManage(id: Long)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onResult(responseOfferList: ResponseOfferList)
    }
}