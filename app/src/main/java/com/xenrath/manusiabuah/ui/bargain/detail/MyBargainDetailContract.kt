package com.xenrath.manusiabuah.ui.bargain.detail

import com.xenrath.manusiabuah.data.model.bargain.ResponseBargainDetail
import com.xenrath.manusiabuah.data.model.bargain.ResponseBargainUpdate

interface MyBargainDetailContract {
    interface Presenter {
        fun getBargainDetail(id: Long)
        fun updateBargainStatus(
            id: Long,
            status: String
        )
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoadingGet(loading: Boolean, message: String? = "Loading...")
        fun onLoadingAction(loading: Boolean)
        fun onResult(responseBargainDetail: ResponseBargainDetail)
        fun onResultUpdate(responseBargainUpdate: ResponseBargainUpdate)
        fun showMessage(message: String)
    }
}