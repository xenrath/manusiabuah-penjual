package com.xenrath.manusiabuah.ui.main

class MainPresenter(val view: MainContract.View) {
    init {
        view.initActivity()
        view.initListener()
    }
}