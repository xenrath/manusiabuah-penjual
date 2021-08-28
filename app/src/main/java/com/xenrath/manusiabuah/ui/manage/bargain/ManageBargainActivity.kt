package com.xenrath.manusiabuah.ui.manage.bargain

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.ui.manage.bargain.tabs.history.ManageBargainHistoryFragment
import kotlinx.android.synthetic.main.activity_bargain.*
import kotlinx.android.synthetic.main.toolbar_custom.*

class ManageBargainActivity : AppCompatActivity(), ManageBargainContract.View {

    lateinit var presenter: ManageBargainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bargain)

        presenter = ManageBargainPresenter(this)
    }

    @SuppressLint("SetTextI18n")
    override fun initActivity() {
        tv_title.text = "Tawaran Saya"

        val adapter = ManageBargainViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(ManageBargainHistoryFragment())
        adapter.addFragment(ManageBargainWaitingFragment())
        view_pager.adapter = adapter
        tabs.setupWithViewPager(view_pager)

        tabs.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_done_all_24)
        tabs.getTabAt(1)!!.setIcon(R.drawable.ic_baseline_access_time_24)
    }

    override fun initListener() {
        iv_back.setOnClickListener {
            onBackPressed()
        }
    }
}