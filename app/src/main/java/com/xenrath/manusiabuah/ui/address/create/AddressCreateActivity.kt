package com.xenrath.manusiabuah.ui.address.create

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.Constant
import com.xenrath.manusiabuah.data.database.PrefManager
import com.xenrath.manusiabuah.data.model.address.ResponseAddressUpdate
import com.xenrath.manusiabuah.data.model.rajaongkir.territory.DataResultsTerritory
import com.xenrath.manusiabuah.data.model.rajaongkir.territory.ResponseRajaongkirTerritory
import com.xenrath.manusiabuah.utils.ApiKey
import com.xenrath.manusiabuah.utils.sweetalert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_create_address.*
import kotlinx.android.synthetic.main.toolbar_custom_delete.*

class AddressCreateActivity : AppCompatActivity(), AddressCreateContract.View {

    lateinit var prefManager: PrefManager
    lateinit var presenter: AddressCreatePresenter

    private lateinit var sLoading: SweetAlertDialog
    private lateinit var sSuccess: SweetAlertDialog
    private lateinit var sError: SweetAlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_address)

        prefManager = PrefManager(this)
        presenter = AddressCreatePresenter(this)

    }

    override fun onStart() {
        super.onStart()
        presenter.getProvince(ApiKey.key)
    }

    @SuppressLint("SetTextI18n")
    override fun initActivity() {
        tv_title.text = "Tambah Alamat"
        iv_delete.visibility = View.GONE

        sLoading = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError = SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal!")
    }

    override fun initListener() {
        btn_save.setOnClickListener {
            val name = et_name.text
            val phone = et_phone.text
            val place = et_place.text
            val address = et_address.text
            val kodePos = et_pos.text
            when {
                name.isEmpty() -> {
                    validationError(et_name, "Nama lengkap tidak boleh kosong!")
                }
                phone.isEmpty() -> {
                    validationError(et_phone, "Nomor telepon tidak boleh kosong!")
                }
                place.isEmpty() -> {
                    validationError(et_place, "Tempat tidak boleh kosong!")
                }
                kodePos.isEmpty() -> {
                    validationError(et_pos, "Kode POS tidak boleh kosong")
                }
                Constant.PROVINCE_ID == "0" -> {
                    showError("Silahkan pilih Provinsi!")
                }
                Constant.CITY_ID == "0" -> {
                    showError("Silahkan pilih Kota/Kabupaten!")
                }
            }
            presenter.addressCreate(
                prefManager.prefId.toString(),
                name.toString(),
                phone.toString(),
                place.toString(),
                address.toString(),
                Constant.PROVINCE_ID,
                Constant.PROVINCE_NAME,
                Constant.CITY_ID,
                Constant.CITY_NAME,
                kodePos.toString(),
            )
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
            true -> progress.visibility = View.VISIBLE
            false -> progress.visibility = View.GONE
        }
    }

    override fun onResult(responseAddressUpdate: ResponseAddressUpdate) {
        val status: Boolean = responseAddressUpdate.status
        val message: String = responseAddressUpdate.message!!

        if (status) {
            showSuccess(message)
        } else {
            showError(message)
        }
    }

    override fun onResultProvince(responseRajaongkirTerritory: ResponseRajaongkirTerritory) {
        val status: Int = responseRajaongkirTerritory.rajaongkir.status.code
        val results = responseRajaongkirTerritory.rajaongkir.results

        if (status == 200) {
            spinProvince(results)
        } else {
            showError("Gagal menampilkan provinsi!")
        }
    }

    override fun onResultCity(responseRajaongkirTerritory: ResponseRajaongkirTerritory) {
        val status: Int = responseRajaongkirTerritory.rajaongkir.status.code
        val results = responseRajaongkirTerritory.rajaongkir.results

        if (status == 200) {
            spinCity(results)
        } else {
            showError("Gagal menampilkan provinsi!")
        }
    }

    override fun spinProvince(results: List<DataResultsTerritory>) {
        layout_province.visibility = View.VISIBLE

        val arrayString = ArrayList<String>()
        arrayString.add("Pilih Provinsi")
        for (prov in results) {
            arrayString.add(prov.province!!)
        }
        val adapter = ArrayAdapter(this, R.layout.item_spiner, arrayString.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin_province.adapter = adapter
        spin_province.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0) {
                    Constant.PROVINCE_ID = "0"
                    layout_city.visibility = View.GONE
                } else {
                    val provId = results[position - 1].province_id!!
                    val provName = results[position - 1].province!!
                    Constant.PROVINCE_ID = provId
                    Constant.PROVINCE_NAME = provName
                    presenter.getCity(ApiKey.key, provId)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }

    override fun spinCity(results: List<DataResultsTerritory>) {
        layout_city.visibility = View.VISIBLE

        val arrayString = ArrayList<String>()
        arrayString.add("Pilih Kota / Kabupaten")
        for (city in results) {
            arrayString.add(city.type + " " + city.city_name)
        }
        val adapter = ArrayAdapter(this, R.layout.item_spiner, arrayString.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin_district.adapter = adapter
        spin_district.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0) {
                    Constant.PROVINCE_ID = "0"
                    layout_pos.visibility = View.GONE
                    et_pos.setText("")
                } else {
                    val cityId = results[position - 1].city_id!!
                    val cityName = results[position - 1].city_name!!
                    val postalCode = results[position - 1].postal_code!!
                    Constant.CITY_ID = cityId
                    Constant.CITY_NAME = cityName
                    layout_pos.visibility = View.VISIBLE
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
                it.dismiss()
            }
            .show()
    }

    override fun validationError(editText: EditText, message: String) {
        editText.error = message
        editText.requestFocus()
    }
}