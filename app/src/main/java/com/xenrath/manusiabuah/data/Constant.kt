package com.xenrath.manusiabuah.data

class Constant {
    companion object {
        var IP = "http://192.168.1.5/tugas-akhir/fruitman/"
        var IP_IMAGE = IP + "public/storage/uploads/"

        var LATITUDE: String = ""
        var LONGITUDE: String = ""
        const val LOCATION_PERMISSION_REQUEST_CODE = 1

        var USER_ID: Long = 0
        var PRODUCT_ID: Long = 0
        var ACCOUNT_ID: Long = 0
        var ADDRESS_ID: Long = 0

        var PROVINCE_ID: String = "0"
        var PROVINCE_NAME: String = "Pilih Provinsi"
        var CITY_ID: String = "0"
        var CITY_NAME: String = "Pilih Kota / Kabupaten"

        // Raja Ongkir

        var COURIER: String = ""
        var SERVICE_TYPE: String = ""
        var TOTAL_PRICE: String = ""

        var BANK_ID: String = "0"
        var BANK_NAME: String = "Pilih Bank"

        var OFFER_ID: Long = 0
        var TRANSACTION_ID: Long = 0
    }
}