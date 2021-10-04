package com.xenrath.manusiabuah.ui.address

import com.xenrath.manusiabuah.data.model.address.DataAddress
import com.xenrath.manusiabuah.data.model.address.ResponseAddressList
import com.xenrath.manusiabuah.data.model.address.ResponseAddressUpdate

interface AddressContract {

    interface Presenter {
        fun addressList(user_id: String)
        fun addressNonActive(user_id: Long)
        fun addressActive(id: Long)
        fun addressDelete(id: Long)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onResultList(responseAddressList: ResponseAddressList)
        fun onResultNonActive(responseAddressUpdate: ResponseAddressUpdate)
        fun onResultActive(responseAddressUpdate: ResponseAddressUpdate)
        fun onResultDelete(responseAddressUpdate: ResponseAddressUpdate)
        fun showAlertDelete(address: DataAddress, position: Int)
        fun showSuccess(message: String)
        fun showSuccessDelete(message: String)
        fun showError(message: String)
    }

}