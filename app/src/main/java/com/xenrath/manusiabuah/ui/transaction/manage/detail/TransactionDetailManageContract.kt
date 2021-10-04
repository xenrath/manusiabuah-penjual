package com.xenrath.manusiabuah.ui.transaction.manage.detail

import com.xenrath.manusiabuah.data.model.product.ResponseProductUpdate
import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionDetail
import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionUpdate

interface TransactionDetailManageContract {
    interface Presenter {
        fun transactionDetail(id: Long)
        fun transactionSend(id: Long)
        fun productOut(id: Long, total_item: Int)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onResultDetail(responseTransactionDetail: ResponseTransactionDetail)
        fun onResultUpdate(responseTransactionUpdate: ResponseTransactionUpdate)
        fun onResultProduct(responseProductUpdate: ResponseProductUpdate)
        fun showDialog(image: String)
        fun showAlert()
        fun showSuccess(message: String)
        fun showError(message: String)
    }
}