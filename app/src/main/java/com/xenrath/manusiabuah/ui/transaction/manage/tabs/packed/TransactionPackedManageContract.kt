package com.xenrath.manusiabuah.ui.transaction.manage.tabs.packed

import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionList

interface TransactionPackedManageContract {
    interface Presenter {
        fun transactionPackedManage(id: Long)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onResult(responseTransactionList: ResponseTransactionList)
    }
}