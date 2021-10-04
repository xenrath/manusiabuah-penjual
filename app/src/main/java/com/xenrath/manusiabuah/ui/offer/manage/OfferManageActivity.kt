package com.xenrath.manusiabuah.ui.offer.manage

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.ui.offer.manage.tabs.history.OfferManageHistoryFragment
import com.xenrath.manusiabuah.ui.offer.manage.tabs.waiting.OfferManageWaitingFragment
import kotlinx.android.synthetic.main.activity_bargain.*
import kotlinx.android.synthetic.main.toolbar_custom.*

class OfferManageActivity : AppCompatActivity(), OfferManageContract.View {

    lateinit var presenterOffer: OfferManagePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bargain)

        presenterOffer = OfferManagePresenter(this)
    }

    @SuppressLint("SetTextI18n")
    override fun initActivity() {
        tv_title.text = "Tawaran Saya"

        val adapter = OfferManageViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(OfferManageHistoryFragment())
        adapter.addFragment(OfferManageWaitingFragment())
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