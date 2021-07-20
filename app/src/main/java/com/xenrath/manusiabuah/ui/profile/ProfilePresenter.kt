package com.xenrath.manusiabuah.ui.profile

import com.xenrath.manusiabuah.data.database.PrefManager

class ProfilePresenter(val view: ProfileContract.View): ProfileContract.Presenter {

    override fun doLogin(prefManager: PrefManager) {
        if (prefManager.prefLogin) view.onResultLogin(prefManager)
    }

    override fun doLogout(prefManager: PrefManager) {
        prefManager.logout()
        view.showMessage("Berhasil keluar")
        view.onResultLogout()
    }

}