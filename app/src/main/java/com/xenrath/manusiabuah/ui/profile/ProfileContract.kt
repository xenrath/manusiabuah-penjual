package com.xenrath.manusiabuah.ui.profile

import com.xenrath.manusiabuah.data.database.PrefManager

interface ProfileContract {

    interface Presenter {
        fun doLogin(prefManager: PrefManager)
        fun doLogout(prefManager: PrefManager)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onResultLogin(prefManager: PrefManager)
        fun onResultLogout()
        fun showAlertSuccess(message: String)
        fun showAlert()
    }
}