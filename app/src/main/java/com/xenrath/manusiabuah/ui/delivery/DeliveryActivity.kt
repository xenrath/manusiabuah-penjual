package com.xenrath.manusiabuah.ui.delivery

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.Constant
import com.xenrath.manusiabuah.data.database.PrefManager
import com.xenrath.manusiabuah.data.model.address.DataAddress
import com.xenrath.manusiabuah.data.model.address.ResponseAddressDetail
import com.xenrath.manusiabuah.data.model.bargain.DataBargain
import com.xenrath.manusiabuah.data.model.bargain.ResponseBargainDetail
import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionDetail
import com.xenrath.manusiabuah.data.model.rajaongkir.cost.DataCosts
import com.xenrath.manusiabuah.data.model.rajaongkir.cost.DataResultsCost
import com.xenrath.manusiabuah.data.model.rajaongkir.cost.ResponseRajaongkirCost
import com.xenrath.manusiabuah.ui.address.AddressActivity
import com.xenrath.manusiabuah.utils.ApiKey
import com.xenrath.manusiabuah.utils.CurrencyHelper
import com.xenrath.manusiabuah.utils.sweetalert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_delivery.*
import kotlinx.android.synthetic.main.toolbar_custom.*

class DeliveryActivity : AppCompatActivity(), DeliveryContract.View {

    lateinit var prefManager: PrefManager
    lateinit var presenter: DeliveryPresenter

    lateinit var bargain: DataBargain
    lateinit var address: List<DataAddress>

    lateinit var courier: String
    lateinit var deliveryService: String
    private lateinit var totalTransfer: String

    lateinit var shippingCost: String

    private lateinit var sLoading: SweetAlertDialog

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
        tv_title.text = "Pengiriman"

        sLoading = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
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

    override fun onResultBargain(responseBargainDetail: ResponseBargainDetail) {
        bargain = responseBargainDetail.bargain!!
    }

    @SuppressLint("SetTextI18n")
    override fun onResultAddress(responseAddressDetail: ResponseAddressDetail) {
        address = responseAddressDetail.address
        val a = address
        if (address.isEmpty()) {
            tv_empty.visibility = View.VISIBLE
            layout_address.visibility = View.GONE
            layout_courier.visibility = View.GONE
        } else {
            tv_empty.visibility = View.GONE
            layout_address.visibility = View.VISIBLE

            tv_name.text = a[0].name
            tv_phone.text = a[0].phone
            tv_address.text =
                a[0].address + ", " + a[0].city_name + ", " + a[0].postal_code + ", (" + a[0].place + ")"
            btn_add_address.text = "Ubah alamat"

            layout_courier.visibility = View.VISIBLE
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
        val origin = bargain.product!!.city_id!!
        val destination = address[0].city_id!!
        val weight = Integer.valueOf(bargain.total_item!!)
        presenter.getCost(ApiKey.key, origin, destination, weight, courier.lowercase())
    }

    override fun onResultCost(responseRajaongkirCost: ResponseRajaongkirCost) {
        val dataResultsCost: List<DataResultsCost> = responseRajaongkirCost.rajaongkir.results
        val courier = dataResultsCost[0].code!!
        val costs = dataResultsCost[0].costs!!

        showSpinnerCost(courier, costs)
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
                    setCost(costs[position - 1])
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }

    override fun setCost(costs: DataCosts) {
        val cost = costs.cost!![0]
        layout_cost.visibility = View.VISIBLE
        tv_etd.text = cost.etd
        tv_value.text = cost.value.toString()

        setTotal(cost.value!!)
    }

    override fun setTotal(value: Int) {
        val totalExp = bargain.price_offer!!
        val shippingCost = (Integer.valueOf(bargain.total_item!!) * value).toString()
        tv_total_exp.text = CurrencyHelper.changeToRupiah(totalExp)
        tv_shipping_costs.text = CurrencyHelper.changeToRupiah(shippingCost)
        totalTransfer = (Integer.valueOf(totalExp) + Integer.valueOf(shippingCost)).toString()
        tv_total.text = CurrencyHelper.changeToRupiah(totalTransfer)
    }

    override fun onResultCheckout(responseTransactionDetail: ResponseTransactionDetail) {

    }

    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}