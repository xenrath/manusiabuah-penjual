package com.xenrath.manusiabuah.ui.home.tabs.seller

import com.xenrath.manusiabuah.data.model.product.ResponseProductList

interface OtherContract {

    interface Presenter {
        fun getProduct(user_id: String, category: String)
        fun searchProduct(keyword: String)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onResultList(responseProductList: ResponseProductList)
        fun onResultSearch(responseProductList: ResponseProductList)
        fun onLoading(loading: Boolean, message: String? = "Loading...")
    }

}