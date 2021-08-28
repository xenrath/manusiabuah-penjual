package com.xenrath.manusiabuah.ui.manage.purchase.detail

import com.xenrath.manusiabuah.data.model.bargain.ResponseBargainDetail
import com.xenrath.manusiabuah.data.model.bargain.ResponseBargainUpdate
import com.xenrath.manusiabuah.data.model.transaction.DataTransaction
import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionDetail

interface ManagePurchaseDetailContract {
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
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onResult(responseTransactionDetail: ResponseTransactionDetail)
        fun onResultUpdate(responseBargainUpdate: ResponseBargainUpdate)
        fun showAlertPack(transaction: DataTransaction)
        fun showAlertSend(transaction: DataTransaction)
    }
}