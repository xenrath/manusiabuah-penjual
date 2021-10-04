package com.xenrath.manusiabuah.ui.address.create

import android.widget.EditText
import com.xenrath.manusiabuah.data.model.address.ResponseAddressUpdate
import com.xenrath.manusiabuah.data.model.rajaongkir.territory.DataResultsTerritory
import com.xenrath.manusiabuah.data.model.rajaongkir.territory.ResponseRajaongkirTerritory

interface AddressCreateContract {

    interface Presenter {
        fun getProvince(key: String)
        fun getCity(key: String, id: String)
        fun addressCreate(
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
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onLoadingTerritory(loading: Boolean)
        fun onResult(responseAddressUpdate: ResponseAddressUpdate)
        fun onResultProvince(responseRajaongkirTerritory: ResponseRajaongkirTerritory)
        fun onResultCity(responseRajaongkirTerritory: ResponseRajaongkirTerritory)
        fun spinProvince(results: List<DataResultsTerritory>)
        fun spinCity(results: List<DataResultsTerritory>)
        fun showSuccess(message: String)
        fun showError(message: String)
        fun validationError(editText: EditText, message: String)
    }

}