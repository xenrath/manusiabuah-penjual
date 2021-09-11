package com.xenrath.manusiabuah.ui.manage.offer.tabs.waiting

import com.xenrath.manusiabuah.data.model.offer.ResponseOfferList

interface OfferManageWaitingContract {

    interface Presenter {
        fun offerWaitingManage(id: Long)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onResult(responseOfferList: ResponseOfferList)
    }

}