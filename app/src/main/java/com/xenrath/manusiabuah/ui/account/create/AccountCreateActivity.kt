package com.xenrath.manusiabuah.ui.account.create

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.Constant
import com.xenrath.manusiabuah.data.database.PrefManager
import com.xenrath.manusiabuah.data.model.account.ResponseAccountUpdate
import com.xenrath.manusiabuah.data.model.bank.ResponseBankList
import com.xenrath.manusiabuah.utils.sweetalert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_account_create.*
import kotlinx.android.synthetic.main.activity_account_create.btn_save
import kotlinx.android.synthetic.main.toolbar_custom.*

class AccountCreateActivity : AppCompatActivity(), AccountCreateContract.View {

    lateinit var prefManager: PrefManager
    lateinit var presenter: AccountCreatePresenter

    private lateinit var sLoading: SweetAlertDialog
    private lateinit var sSuccess: SweetAlertDialog
    private lateinit var sError: SweetAlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_create)

        prefManager = PrefManager(this)
        presenter = AccountCreatePresenter(this)

    }

    override fun onStart() {
        super.onStart()
        presenter.bankList()
    }

    @SuppressLint("SetTextI18n")
    override fun initActivity() {
        tv_title.text = "Tambah Rekening"

        sLoading = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError = SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal!")
    }

    override fun initListener() {
        iv_back.setOnClickListener {
            onBackPressed()
        }
        btn_save.setOnClickListener {
            val name = et_account_name.text
            val number = et_account_number.text
            when {
                name.isEmpty() -> {
                    validationError(et_account_name, "Nama rekening tidak boleh kosong!")
                }
                number.isEmpty() -> {
                    validationError(et_account_number, "Nomor rekening tidak boleh kosong!")
                }
                Constant.BANK_ID == "0" -> {
                    showAlertError("Pilih Bank terlebih dahulu!")
                }
                else -> {
                    presenter.accountCreate(
                        prefManager.prefId.toString(),
                        Constant.BANK_ID,
                        Constant.BANK_NAME,
                        name.toString(),
                        number.toString()
                    )
                }
            }
        }
    }

    override fun onLoading(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }

    override fun onLoadingBank(loading: Boolean) {
        when (loading) {
            true -> progress_bar.visibility = View.VISIBLE
            false -> progress_bar.visibility = View.GONE
        }
    }

    override fun onResult(responseAccountUpdate: ResponseAccountUpdate) {
        if (responseAccountUpdate.status) {
            showAlertSuccess(responseAccountUpdate.message)
        } else {
            showAlertError(responseAccountUpdate.message)
        }
    }

    override fun onResultBank(responseBankList: ResponseBankList) {
        layout_bank.visibility = View.VISIBLE

        val arrayString = ArrayList<String>()
        arrayString.add("Pilih Bank")
        val listBank = responseBankList.banks!!
        for (bank in listBank) {
            arrayString.add(bank.name!!)
        }
        val adapter = ArrayAdapter(this, R.layout.item_spiner, arrayString.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin_bank.adapter = adapter
        val selection = adapter.getPosition(Constant.BANK_NAME)
        spin_bank.setSelection(selection)
        spin_bank.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position != 0) {
                    val idBank = listBank[position-1].id!!
                    val nameBank = listBank[position-1].name!!
                    Constant.BANK_ID = idBank.toString()
                    Constant.BANK_NAME = nameBank
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

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

    override fun validationError(editText: EditText, message: String) {
        editText.error = message
        editText.requestFocus()
    }
}