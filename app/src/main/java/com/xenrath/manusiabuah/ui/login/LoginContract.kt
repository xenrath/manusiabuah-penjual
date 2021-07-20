package com.xenrath.manusiabuah.ui.login

import com.xenrath.manusiabuah.data.DataUser
import com.xenrath.manusiabuah.data.ResponseLogin
import com.xenrath.manusiabuah.data.database.PrefManager

interface LoginContract {

    interface Presenter {
        fun doLogin(email: String, password: String, level: String)
        fun setPref(prefManager: PrefManager, dataUser: DataUser)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean)
        fun onResult(responseLogin: ResponseLogin)
        fun showMessage(message: String)
    }

}