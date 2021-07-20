package com.xenrath.manusiabuah.ui.product.create

import com.xenrath.manusiabuah.data.database.model.ResponseProductUpdate
import java.io.File

interface ProductCreateContract {

    interface Presenter {
        fun insertProduct(
            user_id: String,
            name: String,
            category: String,
            price: String,
            description: String,
            address: String,
            latitude: String,
            longitude: String,
            image: File,
            stock: String
        )
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean)
        fun onResult(responseProductUpdate: ResponseProductUpdate)
        fun showMessage(message: String)
    }

}