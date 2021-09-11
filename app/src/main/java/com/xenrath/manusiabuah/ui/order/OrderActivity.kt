package com.xenrath.manusiabuah.ui.order

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.Constant
import com.xenrath.manusiabuah.data.database.PrefManager
import com.xenrath.manusiabuah.data.model.address.DataAddress
import com.xenrath.manusiabuah.data.model.address.ResponseAddressDetail
import com.xenrath.manusiabuah.data.model.offer.DataOffer
import com.xenrath.manusiabuah.data.model.offer.ResponseOfferDetail
import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionDetail
import com.xenrath.manusiabuah.data.model.rajaongkir.cost.DataCosts
import com.xenrath.manusiabuah.data.model.rajaongkir.cost.DataResultsCost
import com.xenrath.manusiabuah.data.model.rajaongkir.cost.ResponseRajaongkirCost
import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionUpdate
import com.xenrath.manusiabuah.ui.address.AddressActivity
import com.xenrath.manusiabuah.ui.payment.PaymentActivity
import com.xenrath.manusiabuah.utils.ApiKey
import com.xenrath.manusiabuah.utils.CurrencyHelper
import com.xenrath.manusiabuah.utils.sweetalert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_delivery.*
import kotlinx.android.synthetic.main.toolbar_custom.*

class OrderActivity : AppCompatActivity(), OrderContract.View {

    lateinit var prefManager: PrefManager
    lateinit var presenter: OrderPresenter

    lateinit var offer: DataOffer
    lateinit var address: DataAddress

    lateinit var courier: String
    lateinit var deliveryService: String
    lateinit var dataCosts: DataCosts
    private lateinit var totalTransfer: String

    private lateinit var sLoading: SweetAlertDialog
    private lateinit var sSuccess: SweetAlertDialog
    private lateinit var sError: SweetAlertDialog
    private lateinit var sAlert: SweetAlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery)
    }

    override fun onStart() {
        super.onStart()
        presenter.getBargain(Constant.BARGAIN_ID)
        presenter.getAddress(prefManager.prefId.toString())
    }

    override fun onResume() {
        presenter.getAddress(prefManager.prefId.toString())
        super.onResume()
    }

    @SuppressLint("SetTextI18n")
    override fun initActivity() {
        tv_title.text = "Order Produk"

        sLoading = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError =
            SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal!")
        sAlert = SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("Perhatian!")

        showSpinnerCourier()
    }

    override fun initListener() {
        iv_back.setOnClickListener {
            onBackPressed()
        }
        iv_help.setOnClickListener {

        }
        btn_add_address.setOnClickListener {
            startActivity(Intent(this, AddressActivity::class.java))
        }

        btn_pay.setOnClickListener {
//            presenter.checkout(
//                bargain.id.toString(),
//                address[0].id.toString(),
//                courier,
//                deliveryService,
//                totalTransfer.toInt(),
//                "Menunggu"
//            )
            showAlertOrder("")
        }
    }

    override fun onLoading(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }

    override fun onLoadingAddress(loading: Boolean) {
        when (loading) {
            true -> {
                pb_address.visibility = View.VISIBLE
                tv_empty.visibility = View.GONE
                layout_address.visibility = View.GONE
            }
            false -> {
                pb_address.visibility = View.GONE
            }
        }
    }

    override fun onLoadingCost(loading: Boolean) {
        when (loading) {
            true -> {
                pb_cost.visibility = View.VISIBLE
            }
            false -> {
                pb_cost.visibility = View.GONE
            }
        }
    }

    override fun onResultBargain(responseOfferDetail: ResponseOfferDetail) {
        offer = responseOfferDetail.offer!!
    }

    @SuppressLint("SetTextI18n")
    override fun onResultAddress(responseAddressDetail: ResponseAddressDetail) {
        val status = responseAddressDetail.status
        address = responseAddressDetail.address!!

        if (status) {
            tv_empty.visibility = View.GONE
            layout_address.visibility = View.VISIBLE

            tv_name.text = address.name
            tv_phone.text = address.phone
            tv_address.text =
                address.address + ", " + address.city_name + ", " + address.postal_code + ", (" + address.place + ")"
            btn_add_address.text = "Ubah alamat"

            layout_courier.visibility = View.VISIBLE
        } else {
            tv_empty.visibility = View.VISIBLE
            layout_address.visibility = View.GONE
            layout_courier.visibility = View.GONE
        }
    }

    override fun showSpinnerCourier() {
        val arrayString = ArrayList<String>()
        arrayString.add("Pilih Kurir")
        arrayString.add("JNE")
        arrayString.add("POS")
        arrayString.add("TIKI")

        val adapter = ArrayAdapter<Any>(this, R.layout.item_spiner, arrayString.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin_courier.adapter = adapter

        spin_courier.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position != 0) {
                    tv_empty_cost.visibility = View.GONE
                    courier = spin_courier.selectedItem.toString()
                    getCost(courier)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }

    override fun getCost(courier: String) {
        val origin = offer.product!!.city_id!!
        val destination = address.city_id!!
        val weight = Integer.valueOf(offer.total_item!!)
        presenter.getCost(ApiKey.key, origin, destination, weight, courier.lowercase())
    }

    override fun onResultCost(responseRajaongkirCost: ResponseRajaongkirCost) {
        val dataResultsCost: List<DataResultsCost> = responseRajaongkirCost.rajaongkir.results
        val courier = dataResultsCost[0].code!!
        val costs = dataResultsCost[0].costs!!

        showSpinnerCost(courier, costs)
    }

    override fun onResultOrder(responseTransactionUpdate: ResponseTransactionUpdate) {
        val status: Boolean = responseTransactionUpdate.status
        val message: String = responseTransactionUpdate.message!!

        if (status) {
            showSuccess(message)
            startActivity(Intent(this, PaymentActivity::class.java))
        } else {
            showError(message)
        }
    }

    override fun showSpinnerCost(courier: String, costs: List<DataCosts>) {
        val arrayCost = ArrayList<String>()
        arrayCost.add("Pilih Pengiriman")
        for (cost in costs) {
            arrayCost.add(courier.uppercase() + " " + cost.service + " (" + cost.description + ") ")
        }
        val adapter = ArrayAdapter(this, R.layout.item_spiner, arrayCost.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin_cost.adapter = adapter
        spin_cost.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position != 0) {
                    deliveryService = spin_courier.selectedItem.toString()
                    dataCosts = costs[position - 1]
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }

    override fun setCost() {
        val cost = dataCosts.cost!![0]
        layout_cost.visibility = View.VISIBLE
        tv_etd.text = cost.etd
        tv_value.text = cost.value.toString()

        setTotal(cost.value!!)
    }

    override fun setTotal(value: Int) {
        val totalExp = offer.price_offer!!
        tv_total_exp.text = CurrencyHelper.changeToRupiah(totalExp)
        tv_shipping_costs.text = CurrencyHelper.changeToRupiah(value)
        totalTransfer = (Integer.valueOf(totalExp) + value).toString()
        tv_total.text = CurrencyHelper.changeToRupiah(totalTransfer)
    }

    override fun onResultCheckout(responseTransactionDetail: ResponseTransactionDetail) {

    }

    override fun showSuccess(message: String) {
        sSuccess
            .setContentText(message)
//            .setConfirmText("OK")
//            .setConfirmClickListener {
//                it.dismissWithAnimation()
//                finish()
//            }
            .show()
    }

    override fun showError(message: String) {
        sError
            .setContentText(message)
//            .setConfirmText("OK")
//            .setConfirmClickListener {
//                it.dismissWithAnimation()
//            }
            .show()
    }

    override fun showAlertOrder(message: String) {
        sAlert
            .setContentText("Yakin akan mengorder produk?")
            .setConfirmText("Ya")
            .setConfirmClickListener {
                presenter.transactionOrder(
                    offer.product_id.toString(),
                    address.id.toString(),
                    offer.price_offer!!,
                    offer.total_item!!,
                    courier,
                    dataCosts.service!!,
                    dataCosts.cost!![0].value.toString(),
                    " ",
                    totalTransfer
                )
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