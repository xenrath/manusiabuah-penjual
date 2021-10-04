package com.xenrath.manusiabuah.ui.home.tabs.list.update

import android.widget.EditText
import com.xenrath.manusiabuah.data.model.product.ResponseProductDetail
import com.xenrath.manusiabuah.data.model.product.ResponseProductUpdate
import com.xenrath.manusiabuah.data.model.rajaongkir.territory.ResponseRajaongkirTerritory
import java.io.File

interface ProductUpdateContract {

    interface Presenter {
        fun productDetail(id: Long)
        fun getProvince(key: String)
        fun getCity(key: String, id: String)
        fun productUpdate(
            id: Long,
            name: String,
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
            image: File?,
            stock: String,
        )
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onLoadingTerritory(loading: Boolean)
        fun onResultDetail(responseProductDetail: ResponseProductDetail)
        fun onResultProvince(responseRajaongkirTerritory: ResponseRajaongkirTerritory)
        fun onResultCity(responseRajaongkirTerritory: ResponseRajaongkirTerritory)
        fun onResultUpdate(responseProductUpdate: ResponseProductUpdate)
        fun showSuccess(message: String)
        fun showError(message: String)
        fun validationError(editText: EditText, message: String)
    }

}