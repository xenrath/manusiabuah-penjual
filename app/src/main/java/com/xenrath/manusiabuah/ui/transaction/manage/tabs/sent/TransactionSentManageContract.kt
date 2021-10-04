package com.xenrath.manusiabuah.ui.transaction.manage.tabs.sent

import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionList

interface TransactionSentManageContract {

    interface Presenter {
        fun transactionSentManage(id: Long)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onResult(responseTransactionList: ResponseTransactionList)
    }

}