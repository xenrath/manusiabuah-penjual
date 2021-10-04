package com.xenrath.manusiabuah.ui.transaction.manage.detail

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.Constant
import com.xenrath.manusiabuah.data.model.product.ResponseProductUpdate
import com.xenrath.manusiabuah.data.model.transaction.DataTransaction
import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionDetail
import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionUpdate
import com.xenrath.manusiabuah.utils.CurrencyHelper
import com.xenrath.manusiabuah.utils.GlideHelper
import com.xenrath.manusiabuah.utils.sweetalert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_transaction_manage_detail.*
import kotlinx.android.synthetic.main.toolbar_custom.*

class TransactionDetailManageActivity : AppCompatActivity(), TransactionDetailManageContract.View {

    lateinit var presenter: TransactionDetailManagePresenter

    lateinit var transaction: DataTransaction

    private lateinit var sLoading: SweetAlertDialog
    private lateinit var sSuccess: SweetAlertDialog
    private lateinit var sError: SweetAlertDialog
    private lateinit var sAlert: SweetAlertDialog

    private var alertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_manage_detail)

        presenter = TransactionDetailManagePresenter(this)
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
        btn_send.setOnClickListener {
            showAlert()
        }
        btn_evidence.setOnClickListener {
            showDialog(transaction.proof!!)
        }
    }

    override fun onLoading(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onResultDetail(responseTransactionDetail: ResponseTransactionDetail) {
        val status: Boolean = responseTransactionDetail.status
        val message: String = responseTransactionDetail.message!!

        if (status) {
            transaction = responseTransactionDetail.transaction!!
            val product = transaction.product!!

            tv_status.text = transaction.status

            tv_product_name.text = product.name

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
                "Dikemas" -> {
                    btn_send.visibility = View.VISIBLE
                }
                else -> {
                    btn_send.visibility = View.GONE
                }
            }
        } else {
            showError(message)
        }
    }

    override fun onResultUpdate(responseTransactionUpdate: ResponseTransactionUpdate) {
        val status: Boolean = responseTransactionUpdate.status
        val message: String = responseTransactionUpdate.message!!

        if (status) {
            showSuccess(message)
        } else {
            showError(message)
        }
    }

    override fun onResultProduct(responseProductUpdate: ResponseProductUpdate) {
        val status: Boolean = responseProductUpdate.status
        val message: String = responseProductUpdate.message!!

        if (status) {
            presenter.transactionSend(transaction.id!!)
        } else {
            showError(message)
        }
    }

    @SuppressLint("InflateParams")
    override fun showDialog(image: String) {
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

    override fun showAlert() {
        sAlert
            .setContentText("Kirim pesanan sekarang?")
            .setConfirmText("Ya")
            .setConfirmClickListener {
                it.dismissWithAnimation()
                presenter.productOut(transaction.product!!.id!!, transaction.total_item!!)
            }
            .setCancelText("Batal")
            .setCancelClickListener {
                it.dismiss()
            }
            .show()
        sAlert.setCancelable(true)
    }

    override fun showSuccess(message: String) {
        sSuccess
            .setContentText(message)
            .setConfirmText("OK")
            .setConfirmClickListener {
                it.dismissWithAnimation()

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
}