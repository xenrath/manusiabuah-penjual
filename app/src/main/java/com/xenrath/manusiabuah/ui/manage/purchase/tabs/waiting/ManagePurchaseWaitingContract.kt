package com.xenrath.manusiabuah.ui.manage.purchase.tabs.waiting

import com.xenrath.manusiabuah.data.model.bargain.ResponseBargainList

interface ManagePurchaseWaitingContract {

    interface Presenter {
        fun getBargainWaiting(
            user_id: String,
            status: String
        )
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onResult(responseBargainList: ResponseBargainList)
    }

}