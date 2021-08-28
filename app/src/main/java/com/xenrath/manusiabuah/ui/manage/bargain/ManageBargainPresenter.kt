package com.xenrath.manusiabuah.ui.manage.bargain

class ManageBargainPresenter(val view: ManageBargainContract.View) {

    init {
        view.initActivity()
        view.initListener()
    }

}