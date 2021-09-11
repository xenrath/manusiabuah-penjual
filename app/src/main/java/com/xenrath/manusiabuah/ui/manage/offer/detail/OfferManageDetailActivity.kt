package com.xenrath.manusiabuah.ui.manage.offer.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.Constant
import com.xenrath.manusiabuah.data.model.offer.DataOffer
import com.xenrath.manusiabuah.data.model.offer.ResponseOfferDetail
import com.xenrath.manusiabuah.data.model.offer.ResponseOfferUpdate
import com.xenrath.manusiabuah.utils.CurrencyHelper
import com.xenrath.manusiabuah.utils.GlideHelper
import com.xenrath.manusiabuah.utils.sweetalert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_manage_bargain_detail.*
import kotlinx.android.synthetic.main.toolbar_custom.*

class OfferManageDetailActivity : AppCompatActivity(), OfferManageDetailContract.View {

    lateinit var presenterOffer: OfferManageDetailPresenter

    lateinit var offer: DataOffer

    lateinit var sLoading: SweetAlertDialog
    private lateinit var sSuccess: SweetAlertDialog
    private lateinit var sError: SweetAlertDialog
    private lateinit var sAlert: SweetAlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_bargain_detail)

        presenterOffer = OfferManageDetailPresenter(this)
    }

    override fun onStart() {
        super.onStart()
        presenterOffer.offerDetail(Constant.BARGAIN_ID)
    }

    override fun onDestroy() {
        super.onDestroy()
        Constant.BARGAIN_ID = 0
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
        iv_help.setOnClickListener {

        }
        btn_reject.setOnClickListener {
            showAlertReject(offer)
        }
        btn_accept.setOnClickListener {
            showAlertAccept(offer)
        }
    }

    override fun onLoading(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }

    override fun onResultDetail(responseOfferDetail: ResponseOfferDetail) {
        if (!responseOfferDetail.status) {
            sError
                .setContentText(responseOfferDetail.message)
                .setConfirmText("OK")
                .setConfirmClickListener {
                    it.dismissWithAnimation()
                    onBackPressed()
                }
                .show()
        } else {
            offer = responseOfferDetail.offer!!

            val product = offer.product!!
            val user = offer.user!!

            tv_status.text = offer.status
            GlideHelper.setImage(this, product.image!!, iv_product)
            tv_product_name.text = product.name
            tv_total_item.text = offer.total_item
            tv_price.text = CurrencyHelper.changeToRupiah(offer.price!!)
            tv_price_offer.text = CurrencyHelper.changeToRupiah(offer.price_offer!!)
            tv_user_name.text = user.name

            btn_reject.setOnClickListener {

            }

            when (offer.status) {
                "Menunggu" -> layout_action.visibility = View.VISIBLE
                else -> layout_action.visibility = View.GONE
            }
        }
    }

    override fun onResultUpdate(responseOfferUpdate: ResponseOfferUpdate) {
        if (responseOfferUpdate.status) {
            sSuccess
                .setContentText(responseOfferUpdate.message)
                .setConfirmText("OK")
                .setConfirmClickListener {
                    it.dismissWithAnimation()
                    finish()
                }
                .show()
            layout_action.visibility = View.GONE
        } else {
            sError
                .setContentText(responseOfferUpdate.message)
                .setConfirmText("OK")
                .setConfirmClickListener {
                    it.dismissWithAnimation()
                }
                .show()
        }
    }

    override fun showAlertReject(offer: DataOffer) {
        sAlert
            .setContentText("Yakin menolak tawaran?")
            .setConfirmText("Tolak")
            .setConfirmClickListener {
                presenterOffer.offerReject(offer.id!!)
                it.dismissWithAnimation()
            }
            .setCancelText("Batal")
            .setCancelClickListener {
                it.dismissWithAnimation()
            }
            .show()
        sAlert.setCancelable(true)
    }

    override fun showAlertAccept(offer: DataOffer) {
        sAlert
            .setContentText("Yakin menerima tawaran?")
            .setConfirmText("Terima")
            .setConfirmClickListener {
                presenterOffer.offerAccept(offer.id!!)
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