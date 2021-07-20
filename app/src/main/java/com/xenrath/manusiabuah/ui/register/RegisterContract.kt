package com.xenrath.manusiabuah.ui.register

import com.xenrath.manusiabuah.data.ResponseLogin

interface RegisterContract {

    interface Presenter {
        fun doRegister(
            name: String,
            email: String,
            password: String,
            password_confirmation: String,
            phone: String,
            level: String
        )
    }

    interface View {
        fun initListener()
        fun onLoading(loading: Boolean)
        fun onResult(responseLogin: ResponseLogin)
        fun showMessage(message: String)
    }

}