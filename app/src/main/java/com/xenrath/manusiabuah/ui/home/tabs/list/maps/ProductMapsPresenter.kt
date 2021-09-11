package com.xenrath.manusiabuah.ui.home.tabs.list.maps

class ProductMapsPresenter(val view: ProductMapsContract.View) {
    init {
        view.initActivity()
        view.initListener()
    }
}