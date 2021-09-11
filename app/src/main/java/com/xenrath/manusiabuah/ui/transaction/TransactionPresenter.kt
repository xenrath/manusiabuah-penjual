package com.xenrath.manusiabuah.ui.transaction

class TransactionPresenter(val view: TransactionContract.View) {

    init {
        view.initActivity()
        view.initListener()
    }

}