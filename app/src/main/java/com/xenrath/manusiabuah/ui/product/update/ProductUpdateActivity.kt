package com.xenrath.manusiabuah.ui.product.update

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.Constant
import com.xenrath.manusiabuah.data.database.model.ResponseProductDetail
import com.xenrath.manusiabuah.data.database.model.ResponseProductUpdate
import com.xenrath.manusiabuah.ui.product.ProductMapsActivity
import com.xenrath.manusiabuah.utils.FileUtils
import com.xenrath.manusiabuah.utils.GalleryHelper
import com.xenrath.manusiabuah.utils.GlideHelper
import kotlinx.android.synthetic.main.activity_product_create.*

class ProductUpdateActivity : AppCompatActivity(), ProductUpdateContract.View {

    lateinit var presenter: ProductUpdatePresenter

    private var uri: Uri? = null
    private var pickImage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_create)

        setSupportActionBar(toolbar)

        presenter = ProductUpdatePresenter(this)
        presenter.getDetail(Constant.PRODUCT_ID)
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()
        if (Constant.LATITUDE.isNotEmpty()) {
            et_location.text = "${Constant.LATITUDE}, ${Constant.LONGITUDE}"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Constant.LATITUDE = ""
        Constant.LONGITUDE = ""
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImage && resultCode == Activity.RESULT_OK) {
            uri = data!!.data
            iv_image.setImageURI(uri)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun initActivity() {
        supportActionBar!!.title = "Ubah Produk"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun initListener() {
        et_location.setOnClickListener {
            startActivity(Intent(this, ProductMapsActivity::class.java))
        }

        iv_image.setOnClickListener {
            if (GalleryHelper.permissionGallery(this, this, pickImage)) {
                GalleryHelper.openGallery(this)
            }
        }

        btn_save.setOnClickListener {
            val name = et_name.text
            val price = et_price.text
            val description = et_description.text
            val address = et_address.text
            val location = et_location.text
            val stock = et_stock.text
            if (
                name.isNullOrEmpty() ||
                price.isNullOrEmpty() ||
                description.isNullOrEmpty() ||
                address.isNullOrEmpty() ||
                location.isNullOrEmpty() ||
                stock.isNullOrEmpty()
            ) {
                showMessage("Lengkapi data dengan benar")
            } else {
                presenter.updateProduct(
                    Constant.PRODUCT_ID,
                    name.toString(),
                    price.toString(),
                    description.toString(),
                    address.toString(),
                    Constant.LATITUDE,
                    Constant.LONGITUDE,
                    FileUtils.getFile(this, uri),
                    stock.toString()
                )
            }
        }
    }

    override fun onLoading(loading: Boolean) {
        when(loading) {
            true -> progress_line.visibility = View.VISIBLE
            false -> progress_line.visibility = View.GONE
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onResultDetail(responseProductDetail: ResponseProductDetail) {
        progress_bar.visibility = View.GONE
        tv_location.visibility = View.VISIBLE
        cv_location.visibility = View.VISIBLE

        val product = responseProductDetail.product[0]

        et_name.setText(product.name)
        et_price.setText(product.price)
        et_description.setText(product.description)
        et_address.setText(product.address)

        Constant.LATITUDE = product.latitude!!
        Constant.LONGITUDE = product.longitude!!
        et_location.text = "${Constant.LATITUDE}, ${Constant.LONGITUDE}"

        GlideHelper.setImage(this, Constant.IP_IMAGE + product.image!!, iv_image)

        et_stock.setText(product.stock)
    }

    override fun onResultUpdate(responseProductUpdate: ResponseProductUpdate) {
        showMessage(responseProductUpdate.message)
        finish()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}