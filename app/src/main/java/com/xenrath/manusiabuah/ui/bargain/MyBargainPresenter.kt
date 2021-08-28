package com.xenrath.manusiabuah.ui.bargain

class MyBargainPresenter(val view: MyBargainContract.View) {

    init {
        view.initActivity()
        view.initListener()
    }

}