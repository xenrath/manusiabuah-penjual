package com.xenrath.manusiabuah.ui.payment

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import com.xenrath.manusiabuah.data.model.account.ResponseAccountList
import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionDetail
import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionUpdate
import java.io.File

interface PaymentContract {
    interface Presenter {
        fun transactionDetail(id: Long)
        fun accountList(id: Long)
        fun transactionProof(id: Long, proof: File)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onLoadingAccount(loading: Boolean)
        fun onResultTransactionDetail(responseTransactionDetail: ResponseTransactionDetail)
        fun onResultTransactionProof(responseTransactionUpdate: ResponseTransactionUpdate)
        fun onResultAccountList(responseAccountList: ResponseAccountList)
        fun showDialog(file: File)
        fun showSuccess(message: String)
        fun showError(message: String)
        fun showAlert(file: File)
        fun imagePicker()
        fun copyText(value: String, message: String)
        fun showMessage(message: String)
    }
}