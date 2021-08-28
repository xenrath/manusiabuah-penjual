package com.xenrath.manusiabuah.ui.product

import com.xenrath.manusiabuah.data.model.product.DataProduct
import com.xenrath.manusiabuah.data.model.product.ResponseProductList
import com.xenrath.manusiabuah.data.model.product.ResponseProductUpdate

interface ProductContract {

    interface Presenter {
        fun getProduct(user_id: String)
        fun deleteProduct(id: Long)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onResult(resultProductList: ResponseProductList)
        fun onResultDelete(responseProductUpdate: ResponseProductUpdate)
        fun showAlertDelete(dataProduct: DataProduct, position: Int)
        fun showDialogDetail(dataProduct: DataProduct, position: Int)
        fun showMessage(message: String)
    }

}