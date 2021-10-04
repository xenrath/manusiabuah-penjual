package com.xenrath.manusiabuah.ui.transaction.detail

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.Constant
import com.xenrath.manusiabuah.data.database.PrefManager
import com.xenrath.manusiabuah.data.model.comment.ResponseCommentDetail
import com.xenrath.manusiabuah.data.model.comment.ResponseCommentUpdate
import com.xenrath.manusiabuah.data.model.transaction.DataTransaction
import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionDetail
import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionUpdate
import com.xenrath.manusiabuah.ui.payment.PaymentActivity
import com.xenrath.manusiabuah.utils.CurrencyHelper
import com.xenrath.manusiabuah.utils.GlideHelper
import com.xenrath.manusiabuah.utils.sweetalert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_transaction_detail.*
import kotlinx.android.synthetic.main.bottomsheet_comment.view.*
import kotlinx.android.synthetic.main.toolbar_custom.*

class TransactionDetailActivity : AppCompatActivity(), TransactionDetailContract.View {

    lateinit var prefManager: PrefManager
    lateinit var presenter: TransactionDetailPresenter

    lateinit var transaction: DataTransaction

    private lateinit var sLoading: SweetAlertDialog
    private lateinit var sSuccess: SweetAlertDialog
    private lateinit var sError: SweetAlertDialog
    private lateinit var sAlert: SweetAlertDialog

    private var alertDialog: AlertDialog? = null

    private var dialog: BottomSheetDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_detail)

        prefManager = PrefManager(this)
        presenter = TransactionDetailPresenter(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.transactionDetail(Constant.TRANSACTION_ID)
    }

    override fun onDestroy() {
        super.onDestroy()
        Constant.TRANSACTION_ID = 0
    }

    @SuppressLint("SetTextI18n")
    override fun initActivity() {
        tv_title.text = "Detail Pembelian"

        sLoading = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError = SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal!")
        sAlert = SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("Konfirmasi!")
    }

    override fun initListener() {
        iv_back.setOnClickListener {
            onBackPressed()
        }
        btn_paid.setOnClickListener {
            startActivity(Intent(this, PaymentActivity::class.java))
        }
        btn_evidence.setOnClickListener {
            showDialogProof(transaction.proof!!)
        }
        btn_accepted.setOnClickListener {
            showAlert()
        }
        btn_comment.setOnClickListener {
            showDialogComment()
        }
    }

    override fun onLoading(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onResultTransactionDetail(responseTransactionDetail: ResponseTransactionDetail) {
        val status: Boolean = responseTransactionDetail.status
        val message: String = responseTransactionDetail.message!!

        if (status) {
            transaction = responseTransactionDetail.transaction!!
            val product = transaction.product!!

            tv_status.text = transaction.status

            tv_product_name.text = product.name
            tv_product_address.text = product.address

            tv_total_item.text = transaction.total_item.toString()
            tv_price.text = CurrencyHelper.changeToRupiah(product.price!!)
            tv_price_offer.text = CurrencyHelper.changeToRupiah(transaction.price!!)

            tv_address_name.text = transaction.recipient
            tv_address_phone.text = transaction.phone
            tv_address_place.text = transaction.place
            tv_address_address.text = "${transaction.origin}"

            tv_courier.text = transaction.courier
            tv_service_type.text = transaction.service_type
            tv_note.text = transaction.note

            when (transaction.status) {
                "Belum dibayar" -> {
                    btn_paid.visibility = View.VISIBLE
                    btn_accepted.visibility = View.GONE
                    btn_comment.visibility = View.GONE
                }
                "Dikirim" -> {
                    btn_paid.visibility = View.GONE
                    btn_accepted.visibility = View.VISIBLE
                    btn_comment.visibility = View.GONE
                }
                "Selesai" -> {
                    btn_paid.visibility = View.GONE
                    btn_accepted.visibility = View.GONE
                    btn_comment.visibility = View.VISIBLE
                }
                else -> {
                    btn_paid.visibility = View.GONE
                    btn_accepted.visibility = View.GONE
                    btn_comment.visibility = View.GONE
                }
            }

            presenter.commentCheck(transaction.id!!)
        } else {
            showError(message)
        }
    }

    override fun onResultTransactionUpdate(responseTransactionUpdate: ResponseTransactionUpdate) {
        val status: Boolean = responseTransactionUpdate.status
        val message: String = responseTransactionUpdate.message!!

        if (status) {
            showSuccess(message)
        } else {
            showError(message)
        }
    }

    override fun onResultCommentUpdate(responseCommentUpdate: ResponseCommentUpdate) {
        val status: Boolean = responseCommentUpdate.status
        val message: String = responseCommentUpdate.message!!

        if (status) {
            showSuccessComment(message)
        } else {
            showError(message)
        }
    }

    override fun onResultCommentCheck(responseCommentDetail: ResponseCommentDetail) {
        val status: Boolean = responseCommentDetail.status

        if (status) {
            btn_comment.visibility = View.VISIBLE
        } else {
            btn_comment.visibility = View.GONE
        }
    }

    override fun showAlert() {
        sAlert
            .setContentText("Pesanan sudah diterima?")
            .setConfirmText("Sudah")
            .setConfirmClickListener {
                it.dismissWithAnimation()
                presenter.transactionAccepted(transaction.id!!)
            }
            .setCancelText("Belum")
            .setCancelClickListener {
                it.dismiss()
            }
            .show()
        sAlert.setCancelable(true)
    }

    @SuppressLint("InflateParams")
    override fun showDialogProof(image: String) {
        val view = layoutInflater
        val layout = view.inflate(R.layout.dialog_image, null)

        val ivImage = layout.findViewById<ImageView>(R.id.iv_image)
        val ivClose = layout.findViewById<ImageView>(R.id.iv_close)

        ivClose.setOnClickListener {
            alertDialog!!.dismiss()
        }
        GlideHelper.setImage(this, image, ivImage)

        alertDialog = AlertDialog.Builder(this, R.style.CustomAlertDialog).create()
        alertDialog!!.setView(layout)
        alertDialog!!.setCancelable(true)
        alertDialog!!.show()
    }

    @SuppressLint("InflateParams")
    override fun showDialogComment() {
        dialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        val view = layoutInflater.inflate(R.layout.bottomsheet_comment, null)

        view.btn_send.setOnClickListener {
            val comment = view.et_comment.text
            when {
                comment.isEmpty() -> {
                    validationError(view.et_comment, "Komentar tidak boleh kosong")
                }
                else -> {
                    presenter.commentCreate(
                        prefManager.prefId.toString(),
                        transaction.product_id!!,
                        comment.toString()
                    )
                }
            }
        }
        dialog!!.setContentView(view)
        dialog!!.setCancelable(true)
        dialog!!.show()
    }

    override fun showSuccess(message: String) {
        sSuccess
            .setContentText(message)
            .setConfirmText("OK")
            .setConfirmClickListener {
                it.dismissWithAnimation()
                finish()
            }
            .show()
    }

    override fun showSuccessComment(message: String) {
        sSuccess
            .setContentText(message)
            .setConfirmText("OK")
            .setConfirmClickListener {
                it.dismissWithAnimation()
                dialog!!.dismiss()
                btn_comment.visibility = View.GONE
            }
            .show()
    }

    override fun showError(message: String) {
        sError
            .setContentText(message)
            .setConfirmText("OK")
            .setConfirmClickListener {
                it.dismiss()
            }
            .show()
    }

    override fun validationError(editText: EditText, message: String) {
        editText.error = message
        editText.requestFocus()
    }
}