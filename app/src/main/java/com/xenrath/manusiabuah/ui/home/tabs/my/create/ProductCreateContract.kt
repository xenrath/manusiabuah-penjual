package com.xenrath.manusiabuah.ui.home.tabs.my.create

import android.widget.EditText
import com.xenrath.manusiabuah.data.model.product.ResponseProductUpdate
import com.xenrath.manusiabuah.data.model.rajaongkir.territory.ResponseRajaongkirTerritory
import java.io.File

interface ProductCreateContract {

    interface Presenter {
        fun getProvince(key: String)
        fun getCity(key: String, id: String)
        fun insertProduct(
            user_id: String,
            name: String,
            category: String,
            price: String,
            description: String,
            address: String,
            province_id: String,
            province_name: String,
            city_id: String,
            city_name: String,
            postal_code: String,
            latitude: String,
            longitude: String,
            image: File,
            stock: String
        )
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onLoadingTerritory(loading: Boolean)
        fun onResult(responseProductUpdate: ResponseProductUpdate)
        fun onResultProvince(responseRajaongkirTerritory: ResponseRajaongkirTerritory)
        fun onResultCity(responseRajaongkirTerritory: ResponseRajaongkirTerritory)
        fun showAlertSuccess(message: String)
        fun showAlertError(message: String)
        fun validationError(editText: EditText, message: String)
    }

}