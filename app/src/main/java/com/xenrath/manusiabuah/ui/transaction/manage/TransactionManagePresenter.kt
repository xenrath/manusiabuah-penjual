package com.xenrath.manusiabuah.ui.transaction.manage

class TransactionManagePresenter(val view: TransactionManageContract.View) {

    init {
        view.initActivity()
        view.initListener()
    }

}