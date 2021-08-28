package com.xenrath.manusiabuah.ui.manage.bargain.tabs.waiting

import com.xenrath.manusiabuah.data.model.bargain.ResponseBargainList

interface ManageBargainWaitingContract {

    interface Presenter {
        fun bargainSellerWaiting(
            user_id: String,
        )
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onResult(responseBargainList: ResponseBargainList)
    }

}