package com.xenrath.manusiabuah.ui.product.detail

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
import com.xenrath.manusiabuah.data.database.PrefManager
import com.xenrath.manusiabuah.data.database.model.DataProduct
import com.xenrath.manusiabuah.data.database.model.ResponseBargain
import com.xenrath.manusiabuah.data.database.model.ResponseProductDetail
import com.xenrath.manusiabuah.utils.CurrencyHelper
import com.xenrath.manusiabuah.utils.GlideHelper
import com.xenrath.manusiabuah.utils.ToolbarHelper
import kotlinx.android.synthetic.main.activity_detail_product.*
import kotlinx.android.synthetic.main.activity_detail_product.btn_bargain
import kotlinx.android.synthetic.main.activity_detail_product.btn_buy
import kotlinx.android.synthetic.main.activity_detail_product.tv_price
import kotlinx.android.synthetic.main.layout_bottom_sheet.view.*
import kotlinx.android.synthetic.main.layout_map.view.*
import kotlinx.android.synthetic.main.toolbar_custom.*

class DetailProductActivity : AppCompatActivity(), DetailProductContract.View, OnMapReadyCallback {

    lateinit var prefManager: PrefManager
    lateinit var presenter: DetailProductPresenter
    lateinit var product: DataProduct

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product)

        presenter = DetailProductPresenter(this)
        prefManager = PrefManager(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.getDetail(Constant.PRODUCT_ID)
    }

    override fun onDestroy() {
        super.onDestroy()
        Constant.PRODUCT_ID = 0
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun initActivity() {
        ToolbarHelper.setToolbar(this, toolbar, "Detail Produk")
    }

    override fun initListener() {

    }

    override fun onLoadingDetail(loading: Boolean) {
        when(loading) {
            true -> progress_line.visibility = View.VISIBLE
            false -> progress_line.visibility = View.GONE
        }
    }

    override fun onLoadingBottomSheet(loading: Boolean) {
        when(loading) {
            true -> progress_line.visibility = View.VISIBLE
            false -> progress_line.visibility = View.GONE
        }
    }

    override fun onResultDetail(responseProductDetail: ResponseProductDetail) {
        product = responseProductDetail.product[0]

        GlideHelper.setImage(this, product.image!!, iv_image)
        tv_user.text = product.user_id
        tv_name.text = product.name
        tv_price.text = CurrencyHelper.changeToRupiah(product.price.toString())
        tv_description.text = product.description

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

    override fun onResultBargain(responseBargain: ResponseBargain) {

    }

    @SuppressLint("InflateParams")
    override fun showDialogBuy(dataProduct: DataProduct) {
        val dialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        val view = layoutInflater.inflate(R.layout.layout_bottom_sheet, null)

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

    @SuppressLint("InflateParams")
    override fun showDialogBargain(dataProduct: DataProduct) {
        val dialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        val view = layoutInflater.inflate(R.layout.layout_bottom_sheet, null)

        view.tv_price.text = CurrencyHelper.changeToRupiah(dataProduct.price.toString())
        view.btn_buy.visibility = View.GONE
        view.btn_bargain.setOnClickListener {
            val priceOffer = view.et_price_offer.text
            if (
                priceOffer.isNullOrEmpty()
            ) {
                showMessage("Harga penawaran harus di isi")
            } else {
                presenter.bargainProduct(
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