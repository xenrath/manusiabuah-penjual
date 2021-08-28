package com.xenrath.manusiabuah.ui.product.maps

class ProductMapsPresenter(val view: ProductMapsContract.View) {
    init {
        view.initActivity()
        view.initListener()
    }
}