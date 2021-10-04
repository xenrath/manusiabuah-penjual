package com.xenrath.manusiabuah.ui.account.create

import android.widget.EditText
import com.xenrath.manusiabuah.data.model.account.ResponseAccountUpdate
import com.xenrath.manusiabuah.data.model.bank.DataBank
import com.xenrath.manusiabuah.data.model.bank.ResponseBankList

interface AccountCreateContract {
    interface Presenter {
        fun bankList()
        fun accountCreate(
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
        fun onResult(responseAccountUpdate: ResponseAccountUpdate)
        fun onResultBank(responseBankList: ResponseBankList)
        fun spinBank(banks: List<DataBank>)
        fun showAlertSuccess(message: String)
        fun showAlertError(message: String)
        fun validationError(editText: EditText, message: String)
    }
}