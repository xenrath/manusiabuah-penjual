package com.xenrath.manusiabuah.ui.manage.purchase

class ManagePurchasePresenter(val view: ManagePurchaseContract.View) {

    init {
        view.initActivity()
        view.initListener()
    }

}