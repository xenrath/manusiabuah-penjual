package com.xenrath.manusiabuah.ui.transaction.tabs.packed

import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionList

interface TransactionPackedContract {
    interface Presenter {
        fun transactionPacked(id: Long)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onResult(responseTransactionList: ResponseTransactionList)
    }
}