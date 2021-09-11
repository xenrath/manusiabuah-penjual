package com.xenrath.manusiabuah.ui.offer.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
import kotlinx.android.synthetic.main.activity_my_bargain_detail.*
import kotlinx.android.synthetic.main.toolbar_custom.*

class OfferDetailActivity : AppCompatActivity(), OfferDetailContract.View {

    lateinit var presenter: OfferDetailPresenter

    lateinit var offer: DataOffer

    lateinit var sLoading: SweetAlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_bargain_detail)

        presenter = OfferDetailPresenter(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.offerDetail(Constant.BARGAIN_ID)
    }

    override fun onDestroy() {
        super.onDestroy()
        Constant.BARGAIN_ID = 0
    }

    @SuppressLint("SetTextI18n")
    override fun initActivity() {
        tv_title.text = "Detail Tawaran"
    }

    override fun initListener() {
        iv_back.setOnClickListener {
            onBackPressed()
        }
        iv_help.setOnClickListener {

        }
        btn_cancel.setOnClickListener {
            SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Konfirmasi")
                .setContentText("Yakin membatalkan tawaran?")
                .setConfirmText("Ya, batalkan")
                .setConfirmClickListener {
                    it.dismissWithAnimation()
                    presenter.offerCanceled(offer.id!!)
                }
                .setCancelText("Tutup")
                .setCancelClickListener {
                    it.dismissWithAnimation()
                }.show()
        }
    }

    override fun onLoadingGet(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }

    override fun onLoadingAction(loading: Boolean) {
        val progress = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        when (loading) {
            true -> progress.show()
            false -> progress.dismiss()
        }
    }

    override fun onResult(responseOfferDetail: ResponseOfferDetail) {
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

        btn_order.setOnClickListener {
            Constant.BARGAIN_ID = offer.id!!
            startActivity(Intent(this, OrderActivity::class.java))
        }

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
    }

    override fun onResultUpdate(responseOfferUpdate: ResponseOfferUpdate) {
        showMessage(responseOfferUpdate.message!!)
    }

    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}