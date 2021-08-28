package com.xenrath.manusiabuah.ui.address.add

import android.widget.EditText
import com.xenrath.manusiabuah.data.model.address.ResponseAddressUpdate
import com.xenrath.manusiabuah.data.model.rajaongkir.territory.ResponseRajaongkirTerritory

interface AddAddressContract {

    interface Presenter {
        fun getProvince(key: String)
        fun getCity(key: String, id: String)
        fun insertAddress(
            user_id: String,
            name: String,
            phone: String,
            address: String,
            place: String,
            province_id: String,
            province_name: String,
            city_id: String,
            city_name: String,
            postal_code: String
        )
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean)
        fun onLoadingTerritory(loading: Boolean)
        fun onResult(responseAddressUpdate: ResponseAddressUpdate)
        fun onResultProvince(responseRajaOngkirTerritory: ResponseRajaongkirTerritory)
        fun onResultCity(responseRajaOngkirTerritory: ResponseRajaongkirTerritory)
        fun showMessage(message: String)
        fun validationError(editText: EditText, message: String)
    }

}