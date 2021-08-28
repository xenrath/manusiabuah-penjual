package com.xenrath.manusiabuah.ui.address.add

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.Constant
import com.xenrath.manusiabuah.data.database.PrefManager
import com.xenrath.manusiabuah.data.model.address.ResponseAddressUpdate
import com.xenrath.manusiabuah.data.model.rajaongkir.territory.ResponseRajaongkirTerritory
import com.xenrath.manusiabuah.utils.ApiKey
import com.xenrath.manusiabuah.utils.sweetalert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_add_address.*
import kotlinx.android.synthetic.main.toolbar_custom.*

class AddAddressActivity : AppCompatActivity(), AddAddressContract.View {

    lateinit var prefManager: PrefManager
    lateinit var presenter: AddAddressPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)

        prefManager = PrefManager(this)
        presenter = AddAddressPresenter(this)

    }

    override fun onStart() {
        super.onStart()
        presenter.getProvince(ApiKey.key)
    }

    @SuppressLint("SetTextI18n")
    override fun initActivity() {
        tv_title.text = "Tambah Alamat"
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
                    showMessage("Silahkan pilih Provinsi!")
                }
                Constant.CITY_ID == "0" -> {
                    showMessage("Silahkan pilih Kota/Kabupaten!")
                }
            }
            presenter.insertAddress(
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

    override fun onLoading(loading: Boolean) {
        val sLoading =
            SweetAlertDialog(
                this,
                SweetAlertDialog.PROGRESS_TYPE
            ).setContentText("Loading...")
        when (loading) {
            true -> sLoading.show()
            false -> sLoading.dismiss()
        }
    }

    override fun onLoadingTerritory(loading: Boolean) {
        when (loading) {
            true -> {
                progress.visibility = View.VISIBLE
            }
            false -> {
                progress.visibility = View.GONE
            }
        }
    }

    override fun onResult(responseAddressUpdate: ResponseAddressUpdate) {
        showMessage(responseAddressUpdate.message)
        finish()
    }

    override fun onResultProvince(responseRajaongkirTerritory: ResponseRajaongkirTerritory) {
        layout_province.visibility = View.VISIBLE

        val arrayString = ArrayList<String>()
        arrayString.add("Pilih Provinsi")
        val listProvince = responseRajaongkirTerritory.rajaongkir.results
        for(prov in listProvince) {
            arrayString.add(prov.province!!)
        }
        val adapter = ArrayAdapter(this, R.layout.item_spiner, arrayString.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin_province.adapter = adapter
        spin_province.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position != 0) {
                    val idProv = listProvince[position-1].province_id
                    presenter.getCity(ApiKey.key, idProv!!)
                    Constant.PROVINCE_ID = idProv
                    Constant.PROVINCE_NAME = listProvince[position - 1].province!!
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
        for(city in listDistrict) {
            arrayString.add(city.type + " " + city.city_name)
        }
        val adapter = ArrayAdapter(this, R.layout.item_spiner, arrayString.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin_district.adapter = adapter
        spin_district.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position != 0) {
                    val idCity = listDistrict[position-1].city_id!!
                    Constant.CITY_ID = idCity
                    Constant.CITY_NAME = listDistrict[position-1].city_name!!
                    val postalCode = listDistrict[position-1].postal_code
                    et_pos.setText(postalCode)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }



    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun validationError(editText: EditText, message: String) {
        editText.error = message
        editText.requestFocus()
    }
}