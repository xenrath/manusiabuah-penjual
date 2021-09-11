package com.xenrath.manusiabuah.ui.account.update

import android.widget.EditText
import com.xenrath.manusiabuah.data.model.account.ResponseAccountDetail
import com.xenrath.manusiabuah.data.model.account.ResponseAccountUpdate
import com.xenrath.manusiabuah.data.model.bank.ResponseBankList

interface AccountUpdateContract {
    interface Presenter {
        fun bankList()
        fun accountDetail(id: Long)
        fun accountUpdate(
            id: Long,
            user_id: String,
            bank_id: String,
            bank_name: String,
            name: String,
            number: String
        )
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onLoadingBank(loading: Boolean)
        fun onResultDetail(responseAccountDetail: ResponseAccountDetail)
        fun onResultUpdate(responseAccountUpdate: ResponseAccountUpdate)
        fun onResultBank(responseBankList: ResponseBankList)
        fun showAlertSuccess(message: String)
        fun showAlertError(message: String)
        fun validationError(editText: EditText, message: String)
    }
}