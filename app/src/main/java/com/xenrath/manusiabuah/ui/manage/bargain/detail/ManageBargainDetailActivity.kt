package com.xenrath.manusiabuah.ui.manage.bargain.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.Constant
import com.xenrath.manusiabuah.data.model.bargain.DataBargain
import com.xenrath.manusiabuah.data.model.bargain.ResponseBargainDetail
import com.xenrath.manusiabuah.data.model.bargain.ResponseBargainUpdate
import com.xenrath.manusiabuah.utils.CurrencyHelper
import com.xenrath.manusiabuah.utils.GlideHelper
import com.xenrath.manusiabuah.utils.sweetalert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_manage_bargain_detail.*
import kotlinx.android.synthetic.main.toolbar_custom.*

class ManageBargainDetailActivity : AppCompatActivity(), ManageBargainDetailContract.View {

    lateinit var presenter: ManageBargainDetailPresenter

    lateinit var bargain: DataBargain

    lateinit var sLoading: SweetAlertDialog
    private lateinit var sSuccess: SweetAlertDialog
    private lateinit var sError: SweetAlertDialog
    private lateinit var sAlert: SweetAlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_bargain_detail)

        presenter = ManageBargainDetailPresenter(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.getBargainDetail(Constant.BARGAIN_ID)
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
            showAlertReject(bargain)
        }
        btn_accept.setOnClickListener {
            showAlertAccept(bargain)
        }
    }

    override fun onLoading(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }

    override fun onResultDetail(responseBargainDetail: ResponseBargainDetail) {
        if (!responseBargainDetail.status) {
            sError
                .setContentText(responseBargainDetail.message)
                .setConfirmText("OK")
                .setConfirmClickListener {
                    it.dismissWithAnimation()
                    onBackPressed()
                }
                .show()
        } else {
            bargain = responseBargainDetail.bargain!!

            val product = bargain.product!!
            val user = bargain.user!!

            tv_status.text = bargain.status
            GlideHelper.setImage(this, product.image!!, iv_product)
            tv_product_name.text = product.name
            tv_total_item.text = bargain.total_item
            tv_price.text = CurrencyHelper.changeToRupiah(bargain.price!!)
            tv_price_offer.text = CurrencyHelper.changeToRupiah(bargain.price_offer!!)
            tv_user_name.text = user.name

            btn_reject.setOnClickListener {

            }

            when (bargain.status) {
                "Menunggu" -> layout_action.visibility = View.VISIBLE
                else -> layout_action.visibility = View.GONE
            }
        }
    }

    override fun onResultUpdate(responseBargainUpdate: ResponseBargainUpdate) {
        if (responseBargainUpdate.status) {
            sSuccess
                .setContentText(responseBargainUpdate.message)
                .setConfirmText("OK")
                .setConfirmClickListener {
                    it.dismissWithAnimation()
                    finish()
                }
                .show()
            layout_action.visibility = View.GONE
        } else {
            sError
                .setContentText(responseBargainUpdate.message)
                .setConfirmText("OK")
                .setConfirmClickListener {
                    it.dismissWithAnimation()
                }
                .show()
        }
    }

    override fun showAlertReject(bargain: DataBargain) {
        sAlert
            .setContentText("Yakin menolak tawaran?")
            .setConfirmText("Tolak")
            .setConfirmClickListener {
                presenter.bargainReject(bargain.id!!)
                it.dismissWithAnimation()
            }
            .setCancelText("Batal")
            .setCancelClickListener {
                it.dismissWithAnimation()
            }
            .show()
        sAlert.setCancelable(true)
    }

    override fun showAlertAccept(bargain: DataBargain) {
        sAlert
            .setContentText("Yakin menerima tawaran?")
            .setConfirmText("Terima")
            .setConfirmClickListener {
                presenter.bargainAccept(bargain.id!!)
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