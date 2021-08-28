package com.xenrath.manusiabuah.ui.manage.bargain.detail

import com.xenrath.manusiabuah.data.model.bargain.DataBargain
import com.xenrath.manusiabuah.data.model.bargain.ResponseBargainDetail
import com.xenrath.manusiabuah.data.model.bargain.ResponseBargainUpdate

interface ManageBargainDetailContract {
    interface Presenter {
        fun getBargainDetail(id: Long)
        fun bargainReject(id: Long)
        fun bargainAccept(id: Long)
        fun updateBargainStatus(
            id: Long,
            status: String
        )
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onResultDetail(responseBargainDetail: ResponseBargainDetail)
        fun onResultUpdate(responseBargainUpdate: ResponseBargainUpdate)
        fun showAlertReject(bargain: DataBargain)
        fun showAlertAccept(bargain: DataBargain)
    }
}