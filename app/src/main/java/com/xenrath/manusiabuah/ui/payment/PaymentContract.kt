package com.xenrath.manusiabuah.ui.payment

import com.xenrath.manusiabuah.data.model.account.ResponseAccountList
import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionDetail

interface PaymentContract {
    interface Presenter {
        fun transactionDetail(id: Long)
        fun accountList(user_id: Long)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onLoadingAccount(loading: Boolean)
        fun onResultTransactionDetail(responseTransactionDetail: ResponseTransactionDetail)
        fun onResultAccountList(responseAccountList: ResponseAccountList)
        fun showAlertSuccess(message: String)
        fun showAlertError(message: String)
    }
}