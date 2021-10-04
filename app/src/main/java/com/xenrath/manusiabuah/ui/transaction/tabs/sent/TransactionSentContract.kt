package com.xenrath.manusiabuah.ui.transaction.tabs.sent

import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionList

interface TransactionSentContract {

    interface Presenter {
        fun transactionSent(id: Long)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onResult(responseTransactionList: ResponseTransactionList)
    }

}