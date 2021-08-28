package com.xenrath.manusiabuah.ui.product

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.database.PrefManager
import com.xenrath.manusiabuah.data.model.product.DataProduct
import com.xenrath.manusiabuah.data.model.product.ResponseProductList
import com.xenrath.manusiabuah.data.model.product.ResponseProductUpdate
import com.xenrath.manusiabuah.ui.product.create.ProductCreateActivity
import com.xenrath.manusiabuah.ui.product.update.ProductUpdateActivity
import com.xenrath.manusiabuah.utils.GlideHelper
import com.xenrath.manusiabuah.utils.MapsHelper
import com.xenrath.manusiabuah.utils.sweetalert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_my_product.*
import kotlinx.android.synthetic.main.dialog_product.view.*
import kotlinx.android.synthetic.main.toolbar_custom.*

class ProductActivity : AppCompatActivity(), ProductContract.View, OnMapReadyCallback {

    lateinit var prefManager: PrefManager
    lateinit var presenter: ProductPresenter
    lateinit var product: DataProduct
    private lateinit var productAdapter: ProductAdapter
    private lateinit var sLoading: SweetAlertDialog
    private lateinit var sSuccess: SweetAlertDialog
    private lateinit var sAlert: SweetAlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_product)

        prefManager = PrefManager(this)
        presenter = ProductPresenter(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.getProduct(prefManager.prefId.toString())
    }

    @SuppressLint("SetTextI18n")
    override fun initActivity() {
        tv_title.text = "Produk Saya"

        sLoading = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sAlert = SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("Perhatian!")

        MapsHelper.permissionMap(this, this)

        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        productAdapter = ProductAdapter(this, arrayListOf())
        { dataProduct: DataProduct, position: Int, type: String ->
            product = dataProduct
            when (type) {
                "update" -> startActivity(Intent(this, ProductUpdateActivity::class.java))
                "delete" -> showAlertDelete(dataProduct, position)
                "detail" -> showDialogDetail(dataProduct, position)
            }
        }

        rv_product.adapter = productAdapter
        rv_product.layoutManager = layoutManager
    }

    override fun initListener() {
        iv_back.setOnClickListener {
            finish()
        }
        iv_help.setOnClickListener {

        }

        fab.setOnClickListener {
            startActivity(Intent(this, ProductCreateActivity::class.java))
        }
    }

    override fun onLoading(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }

    override fun onResult(resultProductList: ResponseProductList) {
        val products: List<DataProduct> = resultProductList.products!!
        if (products.isEmpty()) {
            rv_product.visibility = View.GONE
            layout_empty_product.visibility = View.VISIBLE
        } else {
            rv_product.visibility = View.VISIBLE
            layout_empty_product.visibility = View.GONE
            productAdapter.setData(products)
        }
    }

    override fun onResultDelete(responseProductUpdate: ResponseProductUpdate) {
        sSuccess
            .setContentText("Produk berhasil dihapus")
            .setConfirmText("OK")
            .setConfirmClickListener {
                it.dismissWithAnimation()
                finish()
            }
            .show()
    }

    override fun showAlertDelete(dataProduct: DataProduct, position: Int) {
        sAlert
            .setContentText("Yakin enghapus produk?")
            .setConfirmText("Hapus")
            .setConfirmClickListener {
                presenter.deleteProduct(dataProduct.id!!)
                productAdapter.removeProduct(position)
                it.dismissWithAnimation()
            }
            .setCancelText("Batal")
            .setCancelClickListener {
                it.dismissWithAnimation()
            }
            .show()
        sAlert.setCancelable(true)
    }

    @SuppressLint("InflateParams")
    override fun showDialogDetail(dataProduct: DataProduct, position: Int) {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_product, null)

        GlideHelper.setImage(applicationContext, dataProduct.image!!, view.iv_product)

        view.tv_name.text = dataProduct.name
        view.tv_address.text = dataProduct.address

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()
    }

    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng(product.latitude!!.toDouble(), product.longitude!!.toDouble())
        googleMap.addMarker(MarkerOptions().position(latLng).title(product.name))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
    }
}