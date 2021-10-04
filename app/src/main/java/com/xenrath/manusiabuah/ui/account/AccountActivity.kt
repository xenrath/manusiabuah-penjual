package com.xenrath.manusiabuah.ui.account

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.database.PrefManager
import com.xenrath.manusiabuah.data.model.account.DataAccount
import com.xenrath.manusiabuah.data.model.account.ResponseAccountList
import com.xenrath.manusiabuah.data.model.account.ResponseAccountUpdate
import com.xenrath.manusiabuah.ui.account.create.AccountCreateActivity
import com.xenrath.manusiabuah.ui.account.update.AccountUpdateActivity
import com.xenrath.manusiabuah.utils.sweetalert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_account.*
import kotlinx.android.synthetic.main.toolbar_custom.*

class AccountActivity : AppCompatActivity(), AccountContract.View {

    lateinit var prefManager: PrefManager
    lateinit var presenter: AccountPresenter

    private lateinit var sLoading: SweetAlertDialog
    private lateinit var sSuccess: SweetAlertDialog
    private lateinit var sError: SweetAlertDialog
    private lateinit var sAlert: SweetAlertDialog

    private lateinit var accountAdapter: AccountAdapter
    private lateinit var account: DataAccount

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        prefManager = PrefManager(this)
        presenter = AccountPresenter(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.accountList(prefManager.prefId)
    }

    @SuppressLint("SetTextI18n")
    override fun initActivity() {
        tv_title.text = "Rekening Saya"

        sLoading = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError = SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal!")
        sAlert = SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("Perhatian!")

        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        accountAdapter = AccountAdapter(this, arrayListOf())
        { dataAccount: DataAccount, position: Int, type: String ->
            account = dataAccount
            when (type) {
                "update" -> startActivity(Intent(this, AccountUpdateActivity::class.java))
                "delete" -> showAlertDelete(dataAccount, position)
            }
        }

        rv_account.adapter = accountAdapter
        rv_account.layoutManager = layoutManager
    }

    override fun initListener() {
        iv_back.setOnClickListener {
            onBackPressed()
        }
        btn_add.setOnClickListener {
            startActivity(Intent(this, AccountCreateActivity::class.java))
        }
    }

    override fun onLoading(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }

    override fun onResultList(responseAccountList: ResponseAccountList) {
        val status: Boolean = responseAccountList.status
        val message: String = responseAccountList.message!!

        if (status) {
            val accounts: List<DataAccount> = responseAccountList.accounts!!

            layout_empty.visibility = View.GONE
            rv_account.visibility = View.VISIBLE
            accountAdapter.setData(accounts)
        } else {
            rv_account.visibility = View.GONE
            layout_empty.visibility = View.VISIBLE
            tv_empty.text = message
        }
    }

    override fun onResultDelete(responseAccountUpdate: ResponseAccountUpdate) {
        val status: Boolean = responseAccountUpdate.status
        val message: String = responseAccountUpdate.message!!

        if (status) {
            showAlertSuccess(message)
        } else {
            showAlertError(message)
        }
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
                it.dismiss()
            }
            .show()
    }

    override fun showAlertDelete(dataAccount: DataAccount, position: Int) {
        sAlert
            .setContentText("Yakin menghapus rekening?")
            .setConfirmText("Hapus")
            .setConfirmClickListener {
                presenter.accountDelete(dataAccount.id!!)
                accountAdapter.removeAccount(position)
                it.dismissWithAnimation()
            }
            .setCancelText("Batal")
            .setCancelClickListener {
                it.dismiss()
            }
            .show()
        sAlert.setCancelable(true)
    }
}