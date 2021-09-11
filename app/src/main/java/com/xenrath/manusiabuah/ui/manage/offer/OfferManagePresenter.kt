package com.xenrath.manusiabuah.ui.manage.offer

class OfferManagePresenter(val view: OfferManageContract.View) {

    init {
        view.initActivity()
        view.initListener()
    }

}