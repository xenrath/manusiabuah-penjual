package com.xenrath.manusiabuah.ui.offer.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.Constant
import com.xenrath.manusiabuah.data.model.offer.DataOffer
import com.xenrath.manusiabuah.data.model.offer.ResponseOfferDetail
import com.xenrath.manusiabuah.data.model.offer.ResponseOfferUpdate
import com.xenrath.manusiabuah.ui.order.OrderActivity
import com.xenrath.manusiabuah.utils.CurrencyHelper
import com.xenrath.manusiabuah.utils.GlideHelper
import com.xenrath.manusiabuah.utils.sweetalert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_offer_detail.*
import kotlinx.android.synthetic.main.toolbar_custom.*

class OfferDetailActivity : AppCompatActivity(), OfferDetailContract.View {

    lateinit var presenter: OfferDetailPresenter

    lateinit var offer: DataOffer

    private lateinit var sLoading: SweetAlertDialog
    private lateinit var sSuccess: SweetAlertDialog
    private lateinit var sError: SweetAlertDialog
    private lateinit var sAlert: SweetAlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer_detail)

        presenter = OfferDetailPresenter(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.offerDetail(Constant.OFFER_ID)
    }

    override fun onDestroy() {
        super.onDestroy()
        Constant.OFFER_ID = 0
    }

    @SuppressLint("SetTextI18n")
    override fun initActivity() {
        tv_title.text = "Detail Tawaran"

        sLoading = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError = SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal!")
        sAlert = SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("Konfirmasi!")
    }

    override fun initListener() {
        iv_back.setOnClickListener {
            onBackPressed()
        }
        btn_cancel.setOnClickListener {
            showAlert()
        }
        btn_order.setOnClickListener {
            Constant.PRODUCT_ID = offer.product!!.id!!
            startActivity(Intent(this, OrderActivity::class.java))
        }
    }

    override fun onLoading(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }

    override fun onResultDetail(responseOfferDetail: ResponseOfferDetail) {
        val status: Boolean = responseOfferDetail.status
        val message: String = responseOfferDetail.message!!

        if (status) {
            offer = responseOfferDetail.offer!!
            val product = offer.product!!
            val user = product.user!!

            tv_status.text = offer.status
            GlideHelper.setImage(this, product.image!!, iv_product)
            tv_product_name.text = product.name
            tv_price.text = CurrencyHelper.changeToRupiah(offer.price!!)
            tv_price_offer.text = CurrencyHelper.changeToRupiah(offer.price_offer!!)
            tv_total_item.text = offer.total_item
            tv_user_name.text = user.name

            when (offer.status) {
                "Menunggu" -> {
                    btn_order.visibility = View.GONE
                }
                "Diterima" -> {
                    btn_cancel.visibility = View.GONE
                }
                else -> {
                    btn_order.visibility = View.GONE
                    btn_cancel.visibility = View.GONE
                }
            }
        } else {
            showError(message)
        }
    }

    override fun onResultUpdate(responseOfferUpdate: ResponseOfferUpdate) {
        val status: Boolean = responseOfferUpdate.status
        val message: String = responseOfferUpdate.message!!

        if (status) {
            showSuccess(message)
        } else {
            showError(message)
        }
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

    override fun showAlert() {
        sAlert
            .setContentText("Yakin membatalkan tawaran?")
            .setConfirmText("Ya, batalkan")
            .setConfirmClickListener {
                it.dismissWithAnimation()
                presenter.offerCanceled(offer.id!!)
            }
            .setCancelText("Tutup")
            .setCancelClickListener {
                it.dismiss()
            }
            .show()
        sAlert.setCancelable(true)
    }
}