package com.xenrath.manusiabuah.utils

import java.text.NumberFormat
import java.util.*

class CurrencyHelper {

    companion object {

        fun changeToRupiah(value: String): String{
            return NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(Integer.valueOf(value))
        }

        fun changeToRupiah(value: Int): String{
            return NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(value)
        }

    }

}