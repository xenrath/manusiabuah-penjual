package com.xenrath.manusiabuah.ui

class MainPresenter(val view: MainContract.View) {

    init {
        view.initListener()
    }

}