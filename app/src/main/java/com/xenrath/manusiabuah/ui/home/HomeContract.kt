package com.xenrath.manusiabuah.ui.home

import com.xenrath.manusiabuah.data.ResponseProduct
import com.xenrath.manusiabuah.data.database.model.ResponseProductList

interface HomeContract {

    interface Presenter {
        fun getProduct(user_id: String, category: String)
        fun searchProduct(keyword: String)
    }

    interface View {
        fun initListener(view: android.view.View)
        fun onResultList(responseProductList: ResponseProductList)
        fun onResultSearch(responseProduct: ResponseProduct)
        fun onLoading(loading: Boolean)
    }

}