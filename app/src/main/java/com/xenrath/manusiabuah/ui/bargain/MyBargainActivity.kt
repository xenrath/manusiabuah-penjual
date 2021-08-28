package com.xenrath.manusiabuah.ui.bargain

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.ui.bargain.tabs.accepted.MyBargainAcceptedFragment
import com.xenrath.manusiabuah.ui.bargain.tabs.history.MyBargainHistoryFragment
import com.xenrath.manusiabuah.ui.bargain.tabs.waiting.MyBargainWaitingFragment
import kotlinx.android.synthetic.main.activity_bargain.*
import kotlinx.android.synthetic.main.toolbar_custom.*

class MyBargainActivity : AppCompatActivity(), MyBargainContract.View {

    lateinit var presenter: MyBargainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bargain)

        presenter = MyBargainPresenter(this)
    }

    @SuppressLint("SetTextI18n")
    override fun initActivity() {
        tv_title.text = "Tawaran Saya"

        val adapter = MyBargainViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(MyBargainWaitingFragment())
        adapter.addFragment(MyBargainAcceptedFragment())
        adapter.addFragment(MyBargainHistoryFragment())
        view_pager.adapter = adapter
        tabs.setupWithViewPager(view_pager)

        tabs.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_access_time_24)
        tabs.getTabAt(1)!!.setIcon(R.drawable.ic_outline_check_circle_24)
        tabs.getTabAt(2)!!.setIcon(R.drawable.ic_baseline_done_all_24)
    }

    override fun initListener() {
        iv_back.setOnClickListener {
            onBackPressed()
        }
    }
}