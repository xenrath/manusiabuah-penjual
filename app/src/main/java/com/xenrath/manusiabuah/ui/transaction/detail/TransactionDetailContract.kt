package com.xenrath.manusiabuah.ui.transaction.detail

import com.xenrath.manusiabuah.data.model.offer.ResponseOfferUpdate
import com.xenrath.manusiabuah.data.model.transaction.DataTransaction
import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionDetail

interface TransactionDetailContract {
    interface Presenter {
        fun transactionDetail(id: Long)
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
        fun onResultUpdate(responseOfferUpdate: ResponseOfferUpdate)
        fun showAlertPack(transaction: DataTransaction)
        fun showAlertSend(transaction: DataTransaction)
    }
}