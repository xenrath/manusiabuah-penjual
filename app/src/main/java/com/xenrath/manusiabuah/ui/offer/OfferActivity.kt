package com.xenrath.manusiabuah.ui.offer

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.ui.offer.tabs.accepted.OfferAcceptedFragment
import com.xenrath.manusiabuah.ui.offer.tabs.history.OfferHistoryFragment
import com.xenrath.manusiabuah.ui.offer.tabs.waiting.OfferWaitingFragment
import kotlinx.android.synthetic.main.activity_bargain.*
import kotlinx.android.synthetic.main.toolbar_custom.*

class OfferActivity : AppCompatActivity(), OfferContract.View {

    lateinit var presenter: OfferPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bargain)

        presenter = OfferPresenter(this)
    }

    @SuppressLint("SetTextI18n")
    override fun initActivity() {
        tv_title.text = "Tawaran Saya"

        val adapter = OfferViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(OfferWaitingFragment())
        adapter.addFragment(OfferAcceptedFragment())
        adapter.addFragment(OfferHistoryFragment())
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