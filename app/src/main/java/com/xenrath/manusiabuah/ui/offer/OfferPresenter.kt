package com.xenrath.manusiabuah.ui.offer

class OfferPresenter(val view: OfferContract.View) {

    init {
        view.initActivity()
        view.initListener()
    }

}