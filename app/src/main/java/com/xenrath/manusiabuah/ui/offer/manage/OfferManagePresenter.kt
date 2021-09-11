package com.xenrath.manusiabuah.ui.offer.manage

class OfferManagePresenter(val view: OfferManageContract.View) {

    init {
        view.initActivity()
        view.initListener()
    }

}