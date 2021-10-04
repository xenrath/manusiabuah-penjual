package com.xenrath.manusiabuah.ui.payment

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.drjacky.imagepicker.ImagePicker
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.Constant
import com.xenrath.manusiabuah.data.model.account.DataAccount
import com.xenrath.manusiabuah.data.model.account.ResponseAccountList
import com.xenrath.manusiabuah.data.model.transaction.DataTransaction
import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionDetail
import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionUpdate
import com.xenrath.manusiabuah.utils.GlideHelper
import com.xenrath.manusiabuah.utils.sweetalert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.toolbar_custom.*
import java.io.File

class PaymentActivity : AppCompatActivity(), PaymentContract.View {

    lateinit var presenter: PaymentPresenter

    private lateinit var sLoading: SweetAlertDialog
    private lateinit var sSuccess: SweetAlertDialog
    private lateinit var sError: SweetAlertDialog
    private lateinit var sAlert: SweetAlertDialog

    lateinit var paymentAdapter: PaymentAdapter
    lateinit var transaction: DataTransaction

    private var alertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        presenter = PaymentPresenter(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.transactionDetail(Constant.TRANSACTION_ID)
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!
                val fileUri: Uri = uri
                showDialog(File(fileUri.path!!))
            }
        }

    @SuppressLint("SetTextI18n")
    override fun initActivity() {
        tv_title.text = "Pembayaran"

        sLoading = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError = SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal!")
        sAlert = SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("Konfirmasi")

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        paymentAdapter = PaymentAdapter(this, ArrayList())

        rv_account.adapter = paymentAdapter
        rv_account.layoutManager = layoutManager
    }

    override fun initListener() {
        btn_proof.setOnClickListener {
            imagePicker()
        }
        iv_copy_price.setOnClickListener {
            copyText(transaction.total_price.toString(), "Harga total berhasil di salin")
        }
    }

    override fun onLoading(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }

    override fun onLoadingAccount(loading: Boolean) {
        when (loading) {
            true -> progress_bar.visibility = View.VISIBLE
            false -> progress_bar.visibility = View.GONE
        }
    }

    override fun onResultTransactionDetail(responseTransactionDetail: ResponseTransactionDetail) {
        val status: Boolean = responseTransactionDetail.status
        val message: String = responseTransactionDetail.message!!

        if (status) {
            transaction = responseTransactionDetail.transaction!!

            tv_invoice.text = transaction.invoice_number
            tv_status.text = transaction.status
            tv_product_name.text = transaction.product!!.name
            tv_price.text = transaction.price
            tv_cost.text = transaction.cost
            tv_total_price.text = transaction.total_price.toString()

            presenter.accountList(transaction.product!!.user_id!!.toLong())
        } else {
            showError(message)
        }

    }

    override fun onResultTransactionProof(responseTransactionUpdate: ResponseTransactionUpdate) {
        val status: Boolean = responseTransactionUpdate.status
        val message: String = responseTransactionUpdate.message!!

        if (status) {
            showSuccess(message)
        } else {
            showError(message)
        }
    }

    override fun onResultAccountList(responseAccountList: ResponseAccountList) {
        val accounts: List<DataAccount> = responseAccountList.accounts!!
        paymentAdapter.setData(accounts)
    }

    @SuppressLint("InflateParams")
    override fun showDialog(file: File) {
        val view = layoutInflater
        val layout = view.inflate(R.layout.dialog_proof, null)

        val ivProof = layout.findViewById<ImageView>(R.id.iv_proof)
        val btnUpload = layout.findViewById<Button>(R.id.btn_upload)
        val btnOther = layout.findViewById<Button>(R.id.btn_other)

        GlideHelper.setImageFile(this, file, ivProof)

        btnUpload.setOnClickListener {
            showAlert(file)
        }
        btnOther.setOnClickListener {
            imagePicker()
        }

        alertDialog = AlertDialog.Builder(this, R.style.CustomAlertDialog).create()
        alertDialog!!.setView(layout)
        alertDialog!!.setCancelable(true)
        alertDialog!!.show()
    }

    override fun imagePicker() {
        ImagePicker.with(this)
            .crop()
            .createIntentFromDialog { launcher.launch(it) }
    }

    override fun copyText(value: String, message: String) {
        val copyManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val copyText = ClipData.newPlainText("text", value)
        copyManager.setPrimaryClip(copyText)

        showMessage(message)
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showSuccess(message: String) {
        sSuccess
            .setContentText(message)
            .setConfirmText("OK")
            .setConfirmClickListener {
                it.dismissWithAnimation()
                btn_proof.visibility = View.GONE
            }
            .show()
    }

    override fun showError(message: String) {
        sError
            .setContentText(message)
            .setConfirmText("OK")
            .setConfirmClickListener {
                it.dismissWithAnimation()
            }
            .show()
    }

    override fun showAlert(file: File) {
        sAlert
            .setContentText("Bukti pembayaran sudah benar?")
            .setConfirmText("Ya")
            .setConfirmClickListener {
                it.dismissWithAnimation()
                alertDialog!!.dismiss()
                presenter.transactionProof(transaction.id!!, file)
            }
            .setCancelText("Batal")
            .setCancelClickListener {
                it.dismiss()
            }
            .show()
        sAlert.setCancelable(true)
    }
}