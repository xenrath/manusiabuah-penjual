package com.xenrath.manusiabuah.ui.transaction.detail

import android.widget.EditText
import com.xenrath.manusiabuah.data.model.comment.ResponseCommentDetail
import com.xenrath.manusiabuah.data.model.comment.ResponseCommentUpdate
import com.xenrath.manusiabuah.data.model.offer.ResponseOfferUpdate
import com.xenrath.manusiabuah.data.model.transaction.DataTransaction
import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionDetail
import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionUpdate

interface TransactionDetailContract {
    interface Presenter {
        fun transactionDetail(id: Long)
        fun transactionAccepted(id: Long)
        fun commentCheck(id: Long)
        fun commentCreate(user_id: String, product_id: String, comment: String)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onResultTransactionDetail(responseTransactionDetail: ResponseTransactionDetail)
        fun onResultTransactionUpdate(responseTransactionUpdate: ResponseTransactionUpdate)
        fun onResultCommentUpdate(responseCommentUpdate: ResponseCommentUpdate)
        fun onResultCommentCheck(responseCommentDetail: ResponseCommentDetail)
        fun showAlert()
        fun showDialogProof(image: String)
        fun showDialogComment()
        fun showSuccess(message: String)
        fun showSuccessComment(message: String)
        fun showError(message: String)
        fun validationError(editText: EditText, message: String)
    }
}