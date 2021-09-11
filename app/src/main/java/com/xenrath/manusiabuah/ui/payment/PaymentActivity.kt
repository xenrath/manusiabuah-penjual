package com.xenrath.manusiabuah.ui.payment

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.Constant
import com.xenrath.manusiabuah.data.model.account.DataAccount
import com.xenrath.manusiabuah.data.model.account.ResponseAccountList
import com.xenrath.manusiabuah.data.model.transaction.DataTransaction
import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionDetail
import com.xenrath.manusiabuah.utils.sweetalert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.toolbar_custom.*

class PaymentActivity : AppCompatActivity(), PaymentContract.View {

    lateinit var presenter: PaymentPresenter

    private lateinit var sLoading: SweetAlertDialog
    private lateinit var sSuccess: SweetAlertDialog
    private lateinit var sError: SweetAlertDialog

    lateinit var paymentAdapter: PaymentAdapter
    lateinit var transaction: DataTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        presenter = PaymentPresenter(this)
    }

    override fun onStart() {
        super.onStart()

        presenter.transactionDetail(Constant.TRANSACTION_ID)
        presenter.accountList(transaction.product!!.user_id!!.toLong())
    }

    @SuppressLint("SetTextI18n")
    override fun initActivity() {
        tv_title.text = "Pembayaran"

        sLoading = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError = SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal!")

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        paymentAdapter = PaymentAdapter(this, ArrayList())

        rv_account.adapter = paymentAdapter
        rv_account.layoutManager = layoutManager
    }

    override fun initListener() {
        btn_proof.setOnClickListener {

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
        transaction = responseTransactionDetail.transaction!!
    }

    override fun onResultAccountList(responseAccountList: ResponseAccountList) {
        val accounts: List<DataAccount> = responseAccountList.accounts!!
        paymentAdapter.setData(accounts)
    }

    override fun showAlertSuccess(message: String) {
        sSuccess
            .setContentText(message)
            .setConfirmText("OK")
            .setConfirmClickListener {
                it.dismissWithAnimation()
                finish()
            }
            .show()
    }

    override fun showAlertError(message: String) {
        sError
            .setContentText(message)
            .setConfirmText("OK")
            .setConfirmClickListener {
                it.dismissWithAnimation()
            }
            .show()
    }
}