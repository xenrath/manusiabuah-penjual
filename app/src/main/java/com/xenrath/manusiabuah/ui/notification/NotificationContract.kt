package com.xenrath.manusiabuah.ui.notification

import com.xenrath.manusiabuah.data.database.model.DataBargain
import com.xenrath.manusiabuah.data.database.model.ResponseBargainList
import com.xenrath.manusiabuah.data.database.model.ResponseBargainUpdate

interface NotificationContract {

    interface Presenter {
        fun getOfferSend()
        fun getOfferWaiting(id: String)
        fun getOfferHistory(id: String)
        fun offerReject(id: Long)
        fun offerAccept(id: Long)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun initListener()
        fun onLoading(loading: Boolean)
        fun onResultListWaiting(responseBargainList: ResponseBargainList)
        fun onResultListHistory(responseBargainList: ResponseBargainList)
        fun onResultUpdate(responseBargainUpdate: ResponseBargainUpdate)
        fun showDialogReject(dataBargain: DataBargain, position: Int)
        fun showDialogAccept(dataBargain: DataBargain, position: Int)
        fun showMessage(message: String)
    }

}