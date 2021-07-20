package com.xenrath.manusiabuah.data.database

import android.content.Context
import android.content.SharedPreferences
import hu.autsoft.krate.Krate
import hu.autsoft.krate.booleanPref
import hu.autsoft.krate.longPref
import hu.autsoft.krate.stringPref

class PrefManager(context: Context): Krate {

    private val PREF_LOGIN: String = "pref_login"
    private val PREF_ID: String = "pref_id"
    private val PREF_NAME: String = "pref_name"
    private val PREF_EMAIL: String = "pref_email"
    private val PREF_PASSWORD: String = "pref_password"
    private val PREF_PHONE: String = "pref_phone"
    private val PREF_ADDRESS: String = "pref_address"
    private val PREF_IMAGE: String = "pref_image"
    private val PREF_LEVEL: String = "pref_level"

    override val sharedPreferences: SharedPreferences = context.applicationContext.getSharedPreferences(
        "new-fruitman1", Context.MODE_PRIVATE
    )

    var prefLogin by booleanPref(PREF_LOGIN, false)
    var prefId by longPref(PREF_ID, 0L)
    var prefName by stringPref(PREF_NAME, "")
    var prefEmail by stringPref(PREF_EMAIL, "")
    var prefPassword by stringPref(PREF_PASSWORD, "")
    var prefPhone by stringPref(PREF_PHONE, "")
    var prefAddress by stringPref(PREF_ADDRESS, "")
    var prefImage by stringPref(PREF_IMAGE, "")
    var prefLevel by stringPref(PREF_LEVEL, "")

    fun logout() {
        sharedPreferences.edit().clear().apply()
    }

}