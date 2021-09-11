package com.xenrath.manusiabuah.ui.main

import androidx.fragment.app.Fragment

interface MainContract {
    interface View {
        fun initActivity()
        fun initListener()
        fun callFragment(int: Int, fragment: Fragment)
    }
}