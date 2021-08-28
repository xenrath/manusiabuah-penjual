package com.xenrath.manusiabuah.ui.login

import com.xenrath.manusiabuah.data.model.user.DataUser
import com.xenrath.manusiabuah.data.model.user.ResponseUser
import com.xenrath.manusiabuah.data.database.PrefManager

interface LoginContract {

    interface Presenter {
        fun doLogin(email: String, password: String, level: String)
        fun setPref(prefManager: PrefManager, dataUser: DataUser)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean, message: String? = "Loading")
        fun onResult(responseUser: ResponseUser)
        fun showAlertSuccess(message: String)
        fun showAlertError(message: String)
    }

}