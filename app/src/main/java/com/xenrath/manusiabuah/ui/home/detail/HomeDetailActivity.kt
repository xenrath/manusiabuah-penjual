package com.xenrath.manusiabuah.ui.home.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
import com.xenrath.manusiabuah.data.model.product.DataProduct
import com.xenrath.manusiabuah.data.model.product.ResponseProductDetail
import com.xenrath.manusiabuah.data.model.offer.ResponseOfferUpdate
import com.xenrath.manusiabuah.utils.CurrencyHelper
import com.xenrath.manusiabuah.utils.GlideHelper
import com.xenrath.manusiabuah.utils.sweetalert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_detail_product.*
import kotlinx.android.synthetic.main.activity_detail_product.btn_bargain
import kotlinx.android.synthetic.main.activity_detail_product.btn_buy
import kotlinx.android.synthetic.main.activity_detail_product.tv_price
import kotlinx.android.synthetic.main.layout_bottom_sheet.view.*
import kotlinx.android.synthetic.main.layout_map.view.*
import kotlinx.android.synthetic.main.toolbar_custom.*

class HomeDetailActivity : AppCompatActivity(), HomeDetailContract.View, OnMapReadyCallback {

    lateinit var prefManager: PrefManager
    lateinit var presenterHome: HomeDetailPresenter

    lateinit var sLoading: SweetAlertDialog

    lateinit var product: DataProduct
    private lateinit var user: DataUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product)

        presenterHome = HomeDetailPresenter(this)
        prefManager = PrefManager(this)
    }

    override fun onStart() {
        super.onStart()
        presenterHome.getDetail(Constant.PRODUCT_ID)
    }

    override fun onDestroy() {
        super.onDestroy()
        Constant.PRODUCT_ID = 0
    }

    @SuppressLint("SetTextI18n")
    override fun initActivity() {
        tv_title.text = "Detail Produk"

        sLoading = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
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

    @SuppressLint("InflateParams")
    override fun onLoadingBottomSheet(loading: Boolean) {
        val view = layoutInflater.inflate(R.layout.layout_bottom_sheet, null)
        when(loading) {
            true -> view.progress_bar.visibility = View.VISIBLE
            false -> view.progress_bar.visibility = View.GONE
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onResultDetail(responseProductDetail: ResponseProductDetail) {
        product = responseProductDetail.product
        user = product.user!!

        GlideHelper.setImage(this, product.image!!, iv_product_image)
        if (user.image != null) {
            GlideHelper.setImage(this, user.image!!, iv_user_image)
        }
        tv_user_name.text = user.name
        tv_name.text = product.name
        tv_description.text = product.description
        tv_address.text = product.address + product.city_name + product.province_name
        tv_price.text = CurrencyHelper.changeToRupiah(product.price!!)

        btn_buy.setOnClickListener {
            showDialogBuy(product)
        }
        btn_bargain.setOnClickListener {
            showDialogBargain(product)
        }
        btn_location.setOnClickListener {
            showDialogLocation(product)
        }
    }

    override fun onResultBargain(responseOfferUpdate: ResponseOfferUpdate) {

    }

    @SuppressLint("InflateParams", "SetTextI18n")
    override fun showDialogBuy(dataProduct: DataProduct) {
        val dialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        val view = layoutInflater.inflate(R.layout.layout_bottom_sheet, null)

        view.tv_title.text = "Beli"
        view.tv_real.visibility = View.GONE
        view.tv_price.text = CurrencyHelper.changeToRupiah(dataProduct.price.toString())
        view.layout_bargain.visibility = View.GONE
        view.btn_bargain.visibility = View.GONE
        view.view_bottom.visibility = View.GONE
        view.btn_buy.setOnClickListener {
            Toast.makeText(this, "Beli Produk Sekarang", Toast.LENGTH_SHORT).show()
        }
        dialog.setContentView(view)
        dialog.show()
    }

    @SuppressLint("InflateParams", "SetTextI18n")
    override fun showDialogBargain(dataProduct: DataProduct) {
        val dialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        val view = layoutInflater.inflate(R.layout.layout_bottom_sheet, null)

        view.tv_title.text = "Tawar"
        view.tv_price.text = CurrencyHelper.changeToRupiah(dataProduct.price.toString())
        view.btn_buy.visibility = View.GONE
        view.btn_bargain.setOnClickListener {
            val priceOffer = view.et_price_offer.text
            if (
                priceOffer.isNullOrEmpty()
            ) {
                showMessage("Harga penawaran harus di isi")
            } else {
                presenterHome.bargainProduct(
                    prefManager.prefId.toString(),
                    dataProduct.id.toString(),
                    dataProduct.price.toString(),
                    priceOffer.toString(),
                    "1",
                "Menunggu"
                )
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
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        dialog.setContentView(view)
        dialog.show()
    }

    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng(product.latitude!!.toDouble(), product.longitude!!.toDouble())
        googleMap.addMarker(MarkerOptions().position(latLng).title("${product.name} - ${product.user_id}"))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
    }

}