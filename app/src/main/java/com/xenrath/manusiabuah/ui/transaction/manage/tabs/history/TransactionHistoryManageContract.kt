package com.xenrath.manusiabuah.ui.transaction.manage.tabs.history

import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionList

interface TransactionHistoryManageContract {
    interface Presenter {
        fun transactionHistoryManage(id: Long)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onResult(responseTransactionList: ResponseTransactionList)
    }
}