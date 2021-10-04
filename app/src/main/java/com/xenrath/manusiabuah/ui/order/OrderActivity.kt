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
import com.xenrath.manusiabuah.data.model.product.DataProduct
import com.xenrath.manusiabuah.data.model.product.ResponseProductDetail
import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionDetail
import com.xenrath.manusiabuah.data.model.rajaongkir.cost.DataCosts
import com.xenrath.manusiabuah.data.model.rajaongkir.cost.DataResultsCost
import com.xenrath.manusiabuah.data.model.rajaongkir.cost.ResponseRajaongkirCost
import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionUpdate
import com.xenrath.manusiabuah.ui.address.AddressActivity
import com.xenrath.manusiabuah.ui.payment.PaymentActivity
import com.xenrath.manusiabuah.ui.transaction.TransactionActivity
import com.xenrath.manusiabuah.utils.ApiKey
import com.xenrath.manusiabuah.utils.CurrencyHelper
import com.xenrath.manusiabuah.utils.GlideHelper
import com.xenrath.manusiabuah.utils.sweetalert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.toolbar_custom.*

class OrderActivity : AppCompatActivity(), OrderContract.View {

    lateinit var prefManager: PrefManager
    lateinit var presenter: OrderPresenter

    private var address: DataAddress? = null
    private var offer: DataOffer? = null
    lateinit var product: DataProduct
    lateinit var dataCosts: DataCosts

    private lateinit var sLoading: SweetAlertDialog
    private lateinit var sSuccess: SweetAlertDialog
    private lateinit var sError: SweetAlertDialog
    private lateinit var sAlert: SweetAlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        prefManager = PrefManager(this)
        presenter = OrderPresenter(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.offerDetail(Constant.OFFER_ID)
        presenter.productDetail(Constant.PRODUCT_ID)
        presenter.addressChecked(prefManager.prefId)
    }

    override fun onResume() {
        super.onResume()
        presenter.addressChecked(prefManager.prefId)
    }

    @SuppressLint("SetTextI18n")
    override fun initActivity() {
        tv_title.text = "Order Produk"

        sLoading = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError =
            SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal!")
        sAlert = SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("Konfirmasi!")
    }

    override fun initListener() {
        iv_back.setOnClickListener {
            onBackPressed()
        }
        btn_address.setOnClickListener {
            startActivity(Intent(this, AddressActivity::class.java))
        }
        btn_pay.setOnClickListener {
            showAlertOrder("Total pembelian sejumlah \n${Constant.TOTAL_PRICE}\n Lanjutkan pembelian?")
        }
    }

    override fun onLoading(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }

    override fun onLoadingProduct(loading: Boolean) {
        when (loading) {
            true -> {
                pb_product.visibility = View.VISIBLE
                layout_product.visibility = View.GONE
            }
            false -> {
                pb_product.visibility = View.GONE
                layout_product.visibility = View.VISIBLE
            }
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
                tv_empty.visibility = View.VISIBLE
                layout_address.visibility = View.VISIBLE
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

    override fun onResultOffer(responseOfferDetail: ResponseOfferDetail) {
        val status: Boolean = responseOfferDetail.status

        if (status) {
            offer = responseOfferDetail.offer!!
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onResultProduct(responseProductDetail: ResponseProductDetail) {
        val status: Boolean = responseProductDetail.status
        val message: String = responseProductDetail.message!!

        if (status) {
            product = responseProductDetail.product!!

            GlideHelper.setImage(this, product.image!!, iv_product_image)
            tv_product_name.text = product.name

            if (offer != null) {
                if (offer!!.price_offer != product.price) {
                    tv_product_price.text = offer!!.price_offer
                } else {
                    tv_product_price.text = product.price
                }
            } else {
                tv_product_price.text = product.price
            }
            tv_product_address.text =
                "${product.address}, ${product.city_name}, ${product.province_name}"
        } else {
            showError(message)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onResultAddress(responseAddressDetail: ResponseAddressDetail) {
        val status = responseAddressDetail.status

        if (status) {
            address = responseAddressDetail.address!!

            tv_empty.visibility = View.GONE
            tv_name.text = address!!.name
            tv_phone.text = address!!.phone
            tv_address.text =
                address!!.address + ", " + address!!.city_name + ", " + address!!.postal_code + ", (" + address!!.place + ")"
            btn_address.text = "Ubah alamat"

            showSpinnerCourier()
        } else {
            tv_empty.visibility = View.VISIBLE
            layout_address.visibility = View.GONE
            layout_rajaongkir.visibility = View.GONE
        }
    }

    override fun showSpinnerCourier() {
        layout_rajaongkir.visibility = View.VISIBLE

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
                    Constant.COURIER = spin_courier.selectedItem.toString()
                    presenter.getCost(
                        ApiKey.key,
                        product.city_id!!,
                        address!!.city_id!!,
                        1,
                        Constant.COURIER.lowercase()
                    )
                } else {
                    Constant.COURIER = ""
                    layout_service_type.visibility = View.GONE
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }

    override fun onResultCost(responseRajaongkirCost: ResponseRajaongkirCost) {
        val status: Int = responseRajaongkirCost.rajaongkir.status.code
        val message: String = responseRajaongkirCost.rajaongkir.status.desription

        if (status == 200) {
            val dataResultsCost: List<DataResultsCost> = responseRajaongkirCost.rajaongkir.results
            val costs = dataResultsCost[0].costs!!
            showSpinnerServiceType(costs)
        } else {
            showError(message)
        }
    }

    override fun onResultOrder(responseTransactionUpdate: ResponseTransactionUpdate) {
        val status: Boolean = responseTransactionUpdate.status
        val message: String = responseTransactionUpdate.message!!

        if (status) {
            showSuccessOrder(message)
        } else {
            showError(message)
        }
    }

    override fun showSpinnerServiceType(costs: List<DataCosts>) {
        layout_service_type.visibility = View.VISIBLE

        val arrayCost = ArrayList<String>()
        arrayCost.add("Pilih Jenis Layanan")
        for (cost in costs) {
            arrayCost.add(Constant.COURIER.uppercase() + " " + cost.service + " (" + cost.description + ") ")
        }
        val adapter = ArrayAdapter(this, R.layout.item_spiner, arrayCost.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin_cost.adapter = adapter
        spin_cost.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position != 0) {
                    Constant.SERVICE_TYPE = spin_courier.selectedItem.toString()
                    dataCosts = costs[position - 1]
                    setCost(Constant.SERVICE_TYPE)
                } else {
                    Constant.SERVICE_TYPE = ""
                    setCost(Constant.SERVICE_TYPE)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }

    override fun setCost(serviceType: String) {
        if (serviceType == "") {
            tv_empty_cost.visibility = View.VISIBLE
            layout_cost.visibility = View.GONE
            setTotal(0)
        } else {
            tv_empty_cost.visibility = View.GONE
            layout_cost.visibility = View.VISIBLE

            val cost = dataCosts.cost!![0]
            tv_etd.text = cost.etd
            tv_value.text = cost.value.toString()
            setTotal(cost.value!!)
        }
    }

    override fun setTotal(value: Int) {
        if (value == 0) {
            layout_total.visibility = View.GONE
            tv_empty_total.visibility = View.VISIBLE
        } else {
            layout_total.visibility = View.VISIBLE
            tv_empty_total.visibility = View.GONE

            val totalExp = product.price!!
            tv_total_exp.text = CurrencyHelper.changeToRupiah(totalExp)
            tv_shipping_costs.text = CurrencyHelper.changeToRupiah(value)
            Constant.TOTAL_PRICE = (Integer.valueOf(totalExp) + value).toString()
            tv_total.text = CurrencyHelper.changeToRupiah(Constant.TOTAL_PRICE)
        }
    }

    override fun onResultCheckout(responseTransactionDetail: ResponseTransactionDetail) {

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

    override fun showSuccessOrder(message: String) {
        sSuccess
            .setContentText(message)
            .setConfirmText("OK")
            .setConfirmClickListener {
                it.dismissWithAnimation()
                finish()
                startActivity(Intent(this, TransactionActivity::class.java))
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

    override fun showAlertOrder(message: String) {
        sAlert
            .setContentText(message)
            .setConfirmText("Ya")
            .setConfirmClickListener {
                it.dismissWithAnimation()
                when {
                    address == null -> {
                        showError("Pilih alamat terlebih dahulu!")
                    }
                    Constant.COURIER == "" -> {
                        showError("Pilih kurir terlebih dahulu!")
                    }
                    Constant.SERVICE_TYPE == "" -> {
                        showError("Pilih jenis layanan terlebih dahulu!")
                    }
                    else -> {
                        presenter.transactionOrder(
                            prefManager.prefId.toString(),
                            product.id.toString(),
                            address!!.name!!,
                            address!!.phone!!,
                            address!!.place!!,
                            "${address!!.city_name} ${address!!.province_name} (${address!!.postal_code})",
                            "1",
                            product.price!!,
                            Constant.COURIER,
                            Constant.SERVICE_TYPE,
                            dataCosts.cost!![0].etd.toString(),
                            dataCosts.cost!![0].value.toString(),
                            et_note.text.toString(),
                            Constant.TOTAL_PRICE
                        )
                    }
                }
            }
            .setCancelText("Batal")
            .setCancelClickListener {
                it.dismiss()
            }
            .show()
        sAlert.setCancelable(true)
    }
}