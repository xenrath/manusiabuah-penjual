package com.xenrath.manusiabuah.ui.transaction.tabs.paid

import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionList

interface TransactionPaidContract {
    interface Presenter {
        fun transactionPaid(id: Long)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onResult(responseTransactionList: ResponseTransactionList)
    }
}