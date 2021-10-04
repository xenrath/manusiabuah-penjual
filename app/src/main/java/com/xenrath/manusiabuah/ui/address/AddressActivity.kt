package com.xenrath.manusiabuah.ui.address

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.database.PrefManager
import com.xenrath.manusiabuah.data.model.address.DataAddress
import com.xenrath.manusiabuah.data.model.address.ResponseAddressList
import com.xenrath.manusiabuah.data.model.address.ResponseAddressUpdate
import com.xenrath.manusiabuah.ui.address.create.AddressCreateActivity
import com.xenrath.manusiabuah.ui.home.tabs.list.update.ProductUpdateActivity
import com.xenrath.manusiabuah.utils.sweetalert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_address.*
import kotlinx.android.synthetic.main.toolbar_custom.*

class AddressActivity : AppCompatActivity(), AddressContract.View {

    lateinit var prefManager: PrefManager
    lateinit var presenter: AddressPresenter

    private lateinit var sLoading: SweetAlertDialog
    private lateinit var sSuccess: SweetAlertDialog
    private lateinit var sError: SweetAlertDialog
    private lateinit var sAlert: SweetAlertDialog

    private lateinit var addressAdapter: AddressAdapter
    private lateinit var address: DataAddress

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)

        prefManager = PrefManager(this)
        presenter = AddressPresenter(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.addressList(prefManager.prefId.toString())
    }

    override fun onResume() {
        super.onResume()
        presenter.addressList(prefManager.prefId.toString())
    }

    @SuppressLint("SetTextI18n")
    override fun initActivity() {
        tv_title.text = "Alamat Pengiriman"

        sLoading = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError = SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal!")
        sAlert = SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("Perhatian!")

        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        addressAdapter = AddressAdapter(this, arrayListOf(), object : AddressAdapter.Listener {
            override fun onClick(address: DataAddress) {
                presenter.addressNonActive(prefManager.prefId)
            }
        })
        {
            dataAddress: DataAddress, position: Int, type: String ->
            address = dataAddress
            when (type) {
                "update" -> startActivity(Intent(this, ProductUpdateActivity::class.java))
                "delete" -> showAlertDelete(dataAddress, position)
            }
        }
        rv_address.adapter = addressAdapter
        rv_address.layoutManager = layoutManager
    }

    override fun initListener() {
        btn_address.setOnClickListener {
            startActivity(Intent(this, AddressCreateActivity::class.java))
        }
    }

    override fun onLoading(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }

    override fun onResultList(responseAddressList: ResponseAddressList) {
        val status: Boolean = responseAddressList.status
        val message: String = responseAddressList.message!!
        val addresses: List<DataAddress> = responseAddressList.addresses!!

        if (status){
            layout_empty.visibility = View.GONE
            rv_address.visibility = View.VISIBLE
            addressAdapter.setData(addresses)
        } else {
            layout_empty.visibility = View.VISIBLE
            tv_empty.text = message
            rv_address.visibility = View.GONE
        }
    }

    override fun onResultNonActive(responseAddressUpdate: ResponseAddressUpdate) {
        val status: Boolean = responseAddressUpdate.status
        val message: String = responseAddressUpdate.message!!

        if (status) {
            presenter.addressActive(address.id!!)
        } else {
            showError(message)
        }
    }

    override fun onResultActive(responseAddressUpdate: ResponseAddressUpdate) {
        val status: Boolean = responseAddressUpdate.status
        val message: String = responseAddressUpdate.message!!

        if (status) {
            showSuccess(message)
        } else {
            showError(message)
        }
    }

    override fun onResultDelete(responseAddressUpdate: ResponseAddressUpdate) {
        val status: Boolean = responseAddressUpdate.status
        val message: String = responseAddressUpdate.message!!

        if (status) {
            showSuccess(message)
        } else {
            showError(message)
        }
    }

    override fun showAlertDelete(address: DataAddress, position: Int) {
        sAlert
            .setContentText("Yakin menghapus alamat?")
            .setConfirmText("Hapus")
            .setConfirmClickListener {
                presenter.addressDelete(address.id!!)
                addressAdapter.removeProduct(position)
                it.dismissWithAnimation()
            }
            .setCancelText("Batal")
            .setCancelClickListener {
                it.dismiss()
            }
            .show()
        sAlert.setCancelable(true)
    }

    override fun showSuccessDelete(message: String) {
        sSuccess
            .setContentText(message)
            .setConfirmText("OK")
            .setConfirmClickListener {
                it.dismiss()
            }
            .show()
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