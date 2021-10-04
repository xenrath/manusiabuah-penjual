package com.xenrath.manusiabuah.ui.home.tabs.list.create

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.Constant
import com.xenrath.manusiabuah.data.database.PrefManager
import com.xenrath.manusiabuah.data.model.product.ResponseProductUpdate
import com.xenrath.manusiabuah.data.model.rajaongkir.territory.ResponseRajaongkirTerritory
import com.xenrath.manusiabuah.ui.home.tabs.list.maps.ProductMapsActivity
import com.xenrath.manusiabuah.utils.ApiKey
import com.xenrath.manusiabuah.utils.FileUtils
import com.xenrath.manusiabuah.utils.GalleryHelper
import com.xenrath.manusiabuah.utils.sweetalert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_product_create.*
import kotlinx.android.synthetic.main.toolbar_custom.*

class ProductCreateActivity : AppCompatActivity(), ProductCreateContract.View {

    lateinit var prefManager: PrefManager
    lateinit var presenter: ProductCreatePresenter

    private lateinit var sLoading: SweetAlertDialog
    private lateinit var sSuccess: SweetAlertDialog
    private lateinit var sError: SweetAlertDialog

    private var uri: Uri? = null
    private var pickImage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_create)

        presenter = ProductCreatePresenter(this)
        prefManager = PrefManager(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()

        presenter.getProvince(ApiKey.key)
        if (Constant.LATITUDE.isNotEmpty()) {
            layout_location.visibility = View.VISIBLE
            et_location.text = "${Constant.LATITUDE}, ${Constant.LONGITUDE}"
            btn_location.text = "Ubah Titik Lokasi"
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        Constant.LATITUDE = ""
        Constant.LONGITUDE = ""
        Constant.PROVINCE_NAME = ""
        Constant.CITY_NAME = ""
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImage && resultCode == Activity.RESULT_OK) {
            uri = data!!.data
            iv_image.setImageURI(uri)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initActivity() {
        tv_title.text = "Tambah Produk"
        btn_save.text = "Tambah Produk"
        btn_location.text = "Tambah Titik Lokasi"

        sLoading = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError = SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal!")
    }

    override fun initListener() {
        iv_back.setOnClickListener {
            finish()
        }
        btn_location.setOnClickListener {
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
            val kodePos = et_pos.text
            val location = et_location.text
            val stock = et_stock.text
            when {
                name.isEmpty() -> {
                    validationError(et_name, "Nama produk tidak boleh kosong!")
                }
                price.isEmpty() -> {
                    validationError(et_price, "Harga produk tidak boleh kosong!")
                }
                address.isEmpty() -> {
                    validationError(et_address, "Alamat produk tidak boleh kosong!")
                }
                Constant.PROVINCE_ID == "0" -> {
                    showError("Pilih provinsi terlebih dahulu!")
                }
                Constant.CITY_ID == "0" -> {
                    showError("Pilih kota/kabupaten terlebih dahulu!")
                }
                kodePos.isEmpty() -> {
                    validationError(et_pos, "Kode POS tidak boleh kosong")
                }
                location.isEmpty() -> {
                    showError("Titik lokasi harus ditambahkan")
                }
                uri == null -> {
                    showError("Gambah harus ditambahkan!")
                }
                stock.isEmpty() -> {
                    validationError(et_stock, "Stock tidak boleh kosong!")
                }
                else -> {
                    presenter.productCreate(
                        prefManager.prefId.toString(),
                        name.toString(),
                        "Eceran",
                        price.toString(),
                        description.toString(),
                        address.toString(),
                        Constant.PROVINCE_ID,
                        Constant.PROVINCE_NAME,
                        Constant.CITY_ID,
                        Constant.CITY_NAME,
                        kodePos.toString(),
                        Constant.LATITUDE,
                        Constant.LONGITUDE,
                        FileUtils.getFile(this, uri),
                        stock.toString()
                    )
                }
            }
        }
    }

    override fun onLoading(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }

    override fun onLoadingTerritory(loading: Boolean) {
        when (loading) {
            true -> pb_territory.visibility = View.VISIBLE
            false -> pb_territory.visibility = View.GONE
        }
    }

    override fun onResult(responseProductUpdate: ResponseProductUpdate) {
        val status: Boolean = responseProductUpdate.status
        val message: String = responseProductUpdate.message!!

        if (status) {
            showSuccess(message)
        } else {
            showError(message)
        }
    }

    override fun onResultProvince(responseRajaongkirTerritory: ResponseRajaongkirTerritory) {
        layout_province.visibility = View.VISIBLE

        val arrayString = ArrayList<String>()
        arrayString.add("Pilih Provinsi")
        val listProvince = responseRajaongkirTerritory.rajaongkir.results
        for (prov in listProvince) {
            arrayString.add(prov.province!!)
        }
        val adapter = ArrayAdapter(this, R.layout.item_spiner, arrayString.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin_province.adapter = adapter
        val selection = adapter.getPosition(Constant.PROVINCE_NAME)
        spin_province.setSelection(selection)
        spin_province.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position != 0) {
                    val idProv = listProvince[position - 1].province_id!!
                    val nameProv = listProvince[position - 1].province!!
                    presenter.getCity(ApiKey.key, idProv)
                    Constant.PROVINCE_ID = idProv
                    Constant.PROVINCE_NAME = nameProv
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }

    override fun onResultCity(responseRajaongkirTerritory: ResponseRajaongkirTerritory) {
        layout_city.visibility = View.VISIBLE

        val arrayString = ArrayList<String>()
        arrayString.add("Pilih Kota / Kabupaten")
        val listDistrict = responseRajaongkirTerritory.rajaongkir.results
        for (city in listDistrict) {
            arrayString.add(city.type + " " + city.city_name)
        }
        val adapter = ArrayAdapter(this, R.layout.item_spiner, arrayString.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin_city.adapter = adapter
        val selection = adapter.getPosition(Constant.CITY_NAME)
        spin_city.setSelection(selection)
        spin_city.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position != 0) {
                    val city = listDistrict[position - 1]
                    val idCity = city.city_id!!
                    val nameCity = city.type!! + " " + city.city_name!!
                    Constant.CITY_ID = idCity
                    Constant.CITY_NAME = nameCity
                    val postalCode = city.postal_code!!
                    et_pos.visibility = View.VISIBLE
                    et_pos.setText(postalCode)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

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
                it.dismissWithAnimation()
            }
            .show()
    }

    override fun validationError(editText: EditText, message: String) {
        editText.error = message
        editText.requestFocus()
    }
}