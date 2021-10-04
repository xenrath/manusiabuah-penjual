package com.xenrath.manusiabuah.ui.transaction.tabs.history

import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionList

interface TransactionHistoryContract {
    interface Presenter {
        fun transactionHistory(id: Long)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onResult(responseTransactionList: ResponseTransactionList)
    }
}