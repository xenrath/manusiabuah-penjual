package com.xenrath.manusiabuah.ui.bargain.tabs.history

import com.xenrath.manusiabuah.data.model.bargain.ResponseBargainList

interface MyBargainHistoryContract {
    interface Presenter {
        fun getBargainDone(
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