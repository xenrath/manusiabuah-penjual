package com.xenrath.manusiabuah.ui.profile.update

import android.widget.EditText
import com.xenrath.manusiabuah.data.model.user.DataUser
import com.xenrath.manusiabuah.data.database.PrefManager
import com.xenrath.manusiabuah.data.model.user.ResponseUserUpdate
import java.io.File

interface ProfileUpdateContract {

    interface Presenter {
        fun updateProfile(
            id: Long,
            name: String,
            email: String,
            phone: String,
            address: String?,
            image: File?
        )
        fun setPref(
            prefManager: PrefManager,
            dataUser: DataUser
        )
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean)
        fun onResult(responseUserUpdate: ResponseUserUpdate)
        fun showAlertSuccess(message: String)
        fun showAlertError(message: String)
        fun validationError(editText: EditText, message: String)
    }

}