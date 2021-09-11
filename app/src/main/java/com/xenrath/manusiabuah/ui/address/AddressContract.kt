package com.xenrath.manusiabuah.ui.address

import com.xenrath.manusiabuah.data.model.address.ResponseAddressList
import com.xenrath.manusiabuah.data.model.address.ResponseAddressUpdate

interface AddressContract {

    interface Presenter {
        fun getAddress(user_id: String)
        fun checkAddress(
            id: Long,
            user_id: String
        )
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onResultList(responseAddressList: ResponseAddressList)
        fun onResultChoice(responseAddressUpdate: ResponseAddressUpdate)
    }

}