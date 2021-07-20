package com.xenrath.manusiabuah.ui.product

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
import com.xenrath.manusiabuah.data.database.PrefManager
import com.xenrath.manusiabuah.data.database.model.DataProduct
import com.xenrath.manusiabuah.data.database.model.ResponseProductList
import com.xenrath.manusiabuah.data.database.model.ResponseProductUpdate
import com.xenrath.manusiabuah.ui.product.create.ProductCreateActivity
import com.xenrath.manusiabuah.ui.product.update.ProductUpdateActivity
import com.xenrath.manusiabuah.utils.GlideHelper
import com.xenrath.manusiabuah.utils.MapsHelper
import kotlinx.android.synthetic.main.activity_my_product.*
import kotlinx.android.synthetic.main.content_my_product.*
import kotlinx.android.synthetic.main.dialog_product.view.*

class ProductActivity : AppCompatActivity(), ProductContract.View, OnMapReadyCallback {

    lateinit var prefManager: PrefManager
    lateinit var presenter: ProductPresenter
    private lateinit var productAdapter: ProductAdapter
    lateinit var product: DataProduct

    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_product)

        setSupportActionBar(toolbar)

        prefManager = PrefManager(this)
        presenter = ProductPresenter(this)

        userId = prefManager.prefId.toString()
    }

    override fun onStart() {
        super.onStart()
        presenter.getProduct(userId)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun initActivity() {
        supportActionBar!!.title = "Produk Saya"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        MapsHelper.permissionMap(this, this)
    }

    override fun initListener() {
        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        productAdapter = ProductAdapter(this, arrayListOf())
        {
            dataProduct: DataProduct, position: Int, type: String ->
            product = dataProduct
            when (type) {
                "update" -> startActivity(Intent(this, ProductUpdateActivity::class.java))
                "delete" -> showDialogDelete(dataProduct, position)
                "detail" -> showDialogDetail(dataProduct, position)
            }
        }

        rv_product.adapter = productAdapter
        rv_product.layoutManager = layoutManager

        srl_myproduct.setOnRefreshListener {
            presenter.getProduct(userId)
        }

        fab.setOnClickListener {
            startActivity(Intent(this, ProductCreateActivity::class.java))
        }
    }

    override fun onLoading(loading: Boolean) {
        when(loading) {
            true -> srl_myproduct.isRefreshing = true
            false -> srl_myproduct.isRefreshing = false
        }
    }

    override fun onResult(resultProductList: ResponseProductList) {
        val dataProduct: List<DataProduct> = resultProductList.dataProduct
        productAdapter.setData(dataProduct)
    }

    override fun onResultDelete(responseProductUpdate: ResponseProductUpdate) {
        showMessage(responseProductUpdate.message)
    }

    override fun showDialogDelete(dataProduct: DataProduct, position: Int) {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Konfirmasi")
        dialog.setMessage("Hapus ${product.name}?")
        dialog.setPositiveButton("Hapus") { dialog, _ ->
            presenter.deleteProduct(Constant.PRODUCT_ID)
            productAdapter.removeProduct(position)
            dialog.dismiss()
        }
        dialog.setNegativeButton("Batal") { dialog, _ ->
            dialog.dismiss()
        }
        dialog.show()
    }

    @SuppressLint("InflateParams")
    override fun showDialogDetail(dataProduct: DataProduct, position: Int) {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_product, null)

        GlideHelper.setImage(applicationContext, dataProduct.image!!, view.iv_product)

        view.tv_name.text = dataProduct.name
        view.tv_category.text = dataProduct.name
        view.tv_address.text = dataProduct.name

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        view.btn_close.setOnClickListener {
            supportFragmentManager.beginTransaction().remove(mapFragment).commit()
            dialog.dismiss()
        }

        dialog.setCancelable(false)
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