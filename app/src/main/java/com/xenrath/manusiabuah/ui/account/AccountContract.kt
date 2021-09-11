package com.xenrath.manusiabuah.ui.account

import com.xenrath.manusiabuah.data.model.account.DataAccount
import com.xenrath.manusiabuah.data.model.account.ResponseAccountList
import com.xenrath.manusiabuah.data.model.account.ResponseAccountUpdate

interface AccountContract {
    interface Presenter {
        fun accountList(id: Long)
        fun accountDelete(id: Long)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onResultList(responseAccountList: ResponseAccountList)
        fun onResultDelete(responseAccountUpdate: ResponseAccountUpdate)
        fun showAlertSuccess(message: String)
        fun showAlertError(message: String)
        fun showAlertDelete(dataAccount: DataAccount, position: Int)
    }
}