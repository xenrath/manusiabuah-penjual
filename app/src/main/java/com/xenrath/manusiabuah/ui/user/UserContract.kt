package com.xenrath.manusiabuah.ui.user

import com.xenrath.manusiabuah.data.model.user.ResponseUser

interface UserContract {
    interface Presenter {
        fun getUser(id: Long)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onResult(responseUser: ResponseUser)
        fun showMessage(message: String)
    }
}