package com.xenrath.manusiabuah.ui.home.tabs.my.maps

class ProductMapsPresenter(val view: ProductMapsContract.View) {
    init {
        view.initActivity()
        view.initListener()
    }
}