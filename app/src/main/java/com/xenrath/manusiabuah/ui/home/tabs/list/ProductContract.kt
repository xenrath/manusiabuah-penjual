package com.xenrath.manusiabuah.ui.home.tabs.list

import com.xenrath.manusiabuah.data.model.account.ResponseAccountList
import com.xenrath.manusiabuah.data.model.product.DataProduct
import com.xenrath.manusiabuah.data.model.product.ResponseProductList
import com.xenrath.manusiabuah.data.model.product.ResponseProductUpdate

interface ProductContract {
    interface Presenter {
        fun productList(id: Long)
        fun deleteProduct(id: Long)
        fun searchProduct(keyword: String)
        fun accountList(id: Long)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onResultList(responseProductList: ResponseProductList)
        fun onResultDelete(responseProductUpdate: ResponseProductUpdate)
        fun onResultSearch(responseProductList: ResponseProductList)
        fun onResultAccount(responseAccountList: ResponseAccountList)
        fun showAlertSuccess(message: String)
        fun showAlertError(message: String)
        fun showErrorAccount(message: String)
        fun showAlertDelete(dataProduct: DataProduct, position: Int)
        fun showDialogDetail(dataProduct: DataProduct, position: Int)
    }
}