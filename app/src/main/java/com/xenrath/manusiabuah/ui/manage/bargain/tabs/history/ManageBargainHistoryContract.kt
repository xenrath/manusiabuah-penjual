package com.xenrath.manusiabuah.ui.manage.bargain.tabs.history

import com.xenrath.manusiabuah.data.model.bargain.ResponseBargainList

interface ManageBargainHistoryContract {
    interface Presenter {
        fun bargainSellerHistory(
            user_id: String
        )
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onResult(responseBargainList: ResponseBargainList)
    }
}