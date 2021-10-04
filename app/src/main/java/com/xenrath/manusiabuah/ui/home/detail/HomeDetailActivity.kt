package com.xenrath.manusiabuah.ui.home.detail

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.Constant
import com.xenrath.manusiabuah.data.model.user.DataUser
import com.xenrath.manusiabuah.data.database.PrefManager
import com.xenrath.manusiabuah.data.model.comment.DataComment
import com.xenrath.manusiabuah.data.model.comment.ResponseCommentList
import com.xenrath.manusiabuah.data.model.product.DataProduct
import com.xenrath.manusiabuah.data.model.product.ResponseProductDetail
import com.xenrath.manusiabuah.data.model.offer.ResponseOfferUpdate
import com.xenrath.manusiabuah.ui.order.OrderActivity
import com.xenrath.manusiabuah.utils.CurrencyHelper
import com.xenrath.manusiabuah.utils.GlideHelper
import com.xenrath.manusiabuah.utils.sweetalert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_detail_product.*
import kotlinx.android.synthetic.main.layout_bottom_sheet.view.*
import kotlinx.android.synthetic.main.layout_map.view.*
import kotlinx.android.synthetic.main.toolbar_custom.*

class HomeDetailActivity : AppCompatActivity(), HomeDetailContract.View, OnMapReadyCallback {

    lateinit var prefManager: PrefManager
    lateinit var presenter: HomeDetailPresenter

    private lateinit var sLoading: SweetAlertDialog
    private lateinit var sSuccess: SweetAlertDialog
    private lateinit var sError: SweetAlertDialog
    private lateinit var sAlert: SweetAlertDialog

    lateinit var commentAdapter: CommentAdapter

    lateinit var product: DataProduct
    private lateinit var user: DataUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product)

        presenter = HomeDetailPresenter(this)
        prefManager = PrefManager(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.productDetail(Constant.PRODUCT_ID)
    }

    @SuppressLint("SetTextI18n")
    override fun initActivity() {
        tv_title.text = "Detail Produk"

        sLoading = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError = SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal!")
        sAlert = SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("Konfirmasi")

        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        commentAdapter = CommentAdapter(this, ArrayList())

        rv_comment.adapter = commentAdapter
        rv_comment.layoutManager = layoutManager
    }

    override fun initListener() {
        iv_back.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onLoadingDetail(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }

    override fun onLoadingComment(loading: Boolean) {
        when (loading) {
            true -> progress_bar.visibility = View.VISIBLE
            false -> progress_bar.visibility = View.GONE
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onResultDetail(responseProductDetail: ResponseProductDetail) {
        product = responseProductDetail.product!!
        user = product.user!!

        GlideHelper.setImage(this, product.image!!, iv_product_image)
        if (user.image != null) {
            GlideHelper.setImage(this, user.image!!, iv_user_image)
        }
        tv_user_name.text = user.name
        tv_name.text = product.name
        tv_description.text = product.description
        tv_address.text = "${product.address}, ${product.city_name}, ${product.province_name}"
        tv_price.text = CurrencyHelper.changeToRupiah(product.price!!)

        btn_buy.setOnClickListener {
            showDialogBuy(product)
        }
        btn_offer.setOnClickListener {
            showDialogOffer(product)
        }
        btn_location.setOnClickListener {
            showDialogLocation(product)
        }
    }

    override fun onResultComment(responseCommentList: ResponseCommentList) {
        val status: Boolean = responseCommentList.status
        val message: String = responseCommentList.message!!

        if (status) {
            val comments: List<DataComment> = responseCommentList.comments!!

            sv_comment.visibility = View.VISIBLE
            tv_empty.visibility = View.GONE
            commentAdapter.setData(comments)
        } else {
            sv_comment.visibility = View.VISIBLE
            tv_empty.visibility = View.GONE
            tv_empty.text = message
        }
    }

    override fun onResultOffer(responseOfferUpdate: ResponseOfferUpdate) {
        val status: Boolean = responseOfferUpdate.status
        val message: String = responseOfferUpdate.message!!

        if (status) {
            showSuccess(message)
        } else {
            showError(message)
        }
    }

    @SuppressLint("InflateParams", "SetTextI18n")
    override fun showDialogBuy(dataProduct: DataProduct) {
        val dialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        val view = layoutInflater.inflate(R.layout.layout_bottom_sheet, null)

        view.tv_title.text = "Beli"
        view.tv_price.text = CurrencyHelper.changeToRupiah(dataProduct.price.toString())
        view.tv_real.visibility = View.GONE
        view.layout_transaction.visibility = View.GONE
        view.btn_offer.visibility = View.GONE
        view.view_bottom.visibility = View.GONE
        view.btn_buy.setOnClickListener {
            Constant.PRODUCT_ID = dataProduct.id!!
            startActivity(Intent(this, OrderActivity::class.java))
        }
        dialog.setContentView(view)
        dialog.show()
    }

    @SuppressLint("InflateParams", "SetTextI18n")
    override fun showDialogOffer(dataProduct: DataProduct) {
        val dialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        val view = layoutInflater.inflate(R.layout.layout_bottom_sheet, null)

        view.tv_title.text = "Tawar"
        view.tv_price.text = CurrencyHelper.changeToRupiah(dataProduct.price.toString())
        view.btn_buy.visibility = View.GONE
        view.btn_offer.setOnClickListener {
            val priceOffer = view.et_price_offer.text
            when {
                priceOffer.isEmpty() -> {
                    validationError(view.et_price_offer, "Harga tawaran tidak boleh kosong!")
                }
                else -> {
                    sAlert
                        .setContentText("Tawar ${dataProduct.name} dengan harga $priceOffer?")
                        .setConfirmText("Ya")
                        .setConfirmClickListener {
                            presenter.offerCreate(
                                prefManager.prefId.toString(),
                                dataProduct.id.toString(),
                                dataProduct.price.toString(),
                                priceOffer.toString(),
                                "1",
                                "Menunggu"
                            )
                            it.dismiss()
                            dialog.dismiss()
                        }
                        .setCancelText("Batal")
                        .setCancelClickListener {
                            it.dismiss()
                        }
                        .show()
                    sAlert.setCancelable(true)
                }
            }
        }
        dialog.setContentView(view)
        dialog.show()
    }

    @SuppressLint("InflateParams")
    override fun showDialogLocation(dataProduct: DataProduct) {
        val dialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        val view = layoutInflater.inflate(R.layout.layout_map, null)

        view.tv_location.text = dataProduct.address
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        dialog.setContentView(view)
        dialog.show()
    }

    override fun showSuccess(message: String) {
        sSuccess
            .setContentText(message)
            .setConfirmText("OK")
            .setConfirmClickListener {
                it.dismiss()
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

    override fun showAlert(content: String, dataProduct: DataProduct, priceOffer: String) {
        sAlert
            .setContentText(content)
            .setConfirmText("Ya")
            .setConfirmClickListener {
                presenter.offerCreate(
                    prefManager.prefId.toString(),
                    dataProduct.id.toString(),
                    dataProduct.price.toString(),
                    priceOffer,
                    "1",
                    "Menunggu"
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

    override fun validationError(editText: EditText, message: String) {
        TODO("Not yet implemented")
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng(product.latitude!!.toDouble(), product.longitude!!.toDouble())
        googleMap.addMarker(
            MarkerOptions().position(latLng).title("${product.name} - ${product.user_id}")
        )
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
    }

}