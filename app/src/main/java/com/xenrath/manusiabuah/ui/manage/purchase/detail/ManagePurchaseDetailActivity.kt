package com.xenrath.manusiabuah.ui.manage.purchase.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.Constant
import com.xenrath.manusiabuah.data.model.offer.ResponseOfferUpdate
import com.xenrath.manusiabuah.data.model.transaction.DataTransaction
import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionDetail
import com.xenrath.manusiabuah.utils.CurrencyHelper
import com.xenrath.manusiabuah.utils.sweetalert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_manage_purchase_detail.*
import kotlinx.android.synthetic.main.toolbar_custom.*

class ManagePurchaseDetailActivity : AppCompatActivity(), ManagePurchaseDetailContract.View {

    lateinit var presenter: ManagePurchaseDetailPresenter

    lateinit var transaction: DataTransaction

    lateinit var sLoading: SweetAlertDialog
    private lateinit var sSuccess: SweetAlertDialog
    private lateinit var sError: SweetAlertDialog
    private lateinit var sAlert: SweetAlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_purchase_detail)

        presenter = ManagePurchaseDetailPresenter(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.transactionDetail(Constant.BARGAIN_ID)
    }

    override fun onDestroy() {
        super.onDestroy()
        Constant.BARGAIN_ID = 0
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
        iv_help.setOnClickListener {

        }
        btn_pack_order.setOnClickListener {
            showAlertPack(transaction)
        }
        btn_send_order.setOnClickListener {
            showAlertSend(transaction)
        }
    }

    override fun onLoading(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onResult(responseTransactionDetail: ResponseTransactionDetail) {
        transaction = responseTransactionDetail.transaction!!
        val address = transaction.address!!
        val product = transaction.product!!

        tv_status.text = transaction.status

        tv_product_name.text = product.name

        tv_total_item.text = transaction.total_item
        tv_price.text = CurrencyHelper.changeToRupiah(product.price!!)
        tv_price_offer.text = CurrencyHelper.changeToRupiah(transaction.price!!)

        tv_address_name.text = address.name
        tv_address_phone.text = address.phone
        tv_address_place.text = address.place
        tv_address_address.text = "${address.address}, ${address.city_name} - ${address.province_name}, Kode POS: ${address.postal_code}"

        tv_courier.text = transaction.courier
        tv_service_type.text = transaction.service_type
        tv_note.text = transaction.note

        when (transaction.status) {
            "Menunggu" -> {
                btn_pack_order.visibility = View.VISIBLE
                btn_send_order.visibility = View.GONE
            }
            "Dikemas" -> {
                btn_pack_order.visibility = View.GONE
                btn_send_order.visibility = View.VISIBLE
            }
            else -> {
                btn_pack_order.visibility = View.GONE
                btn_send_order.visibility = View.GONE
            }
        }
    }

    override fun onResultUpdate(responseOfferUpdate: ResponseOfferUpdate) {

    }

    override fun showAlertPack(transaction: DataTransaction) {
        sAlert
            .setContentText("Kemas pesanan sekarang?")
            .setConfirmText("Ya")
            .setConfirmClickListener {
                it.dismissWithAnimation()
            }
            .setCancelText("Batal")
            .setCancelClickListener {
                it.dismissWithAnimation()
            }
            .show()
        sAlert.setCancelable(true)
    }

    override fun showAlertSend(transaction: DataTransaction) {
        sAlert
            .setContentText("Kirim pesanan sekarang?")
            .setConfirmText("Ya")
            .setConfirmClickListener {
                it.dismissWithAnimation()
            }
            .setCancelText("Batal")
            .setCancelClickListener {
                it.dismissWithAnimation()
            }
            .show()
        sAlert.setCancelable(true)
    }
}