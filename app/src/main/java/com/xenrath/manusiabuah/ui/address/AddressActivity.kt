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
import com.xenrath.manusiabuah.ui.address.add.AddAddressActivity
import com.xenrath.manusiabuah.utils.sweetalert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_address.*
import kotlinx.android.synthetic.main.toolbar_custom.*

class AddressActivity : AppCompatActivity(), AddressContract.View {

    lateinit var prefManager: PrefManager
    lateinit var presenter: AddressPresenter

    lateinit var sLoading: SweetAlertDialog

    private lateinit var addressAdapter: AddressAdapter
    lateinit var address: DataAddress

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)

        prefManager = PrefManager(this)
        presenter = AddressPresenter(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.getAddress(prefManager.prefId.toString())
    }

    override fun onResume() {
        super.onResume()
        presenter.getAddress(prefManager.prefId.toString())
    }

    @SuppressLint("SetTextI18n")
    override fun initActivity() {
        tv_title.text = "Alamat Pengiriman"

        sLoading = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)

        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        addressAdapter = AddressAdapter(this, arrayListOf())
        {
            dataAddress: DataAddress ->
            address = dataAddress
            presenter.checkAddress(address.id!!, prefManager.prefId.toString())
            onBackPressed()
        }
        rv_address.adapter = addressAdapter
        rv_address.layoutManager = layoutManager
    }

    override fun initListener() {
        btn_add_address.setOnClickListener {
            startActivity(Intent(this, AddAddressActivity::class.java))
        }
    }

    override fun onLoading(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }

    override fun onResultList(responseAddressList: ResponseAddressList) {
        val dataAddress: List<DataAddress> = responseAddressList.address
        if (dataAddress.isEmpty()){
            text_empty.visibility = View.VISIBLE
            rv_address.visibility = View.GONE
        } else {
            text_empty.visibility = View.GONE
            rv_address.visibility = View.VISIBLE
            addressAdapter.setData(dataAddress)
        }
    }

    override fun onResultChoice(responseAddressUpdate: ResponseAddressUpdate) {
        onBackPressed()
    }
}