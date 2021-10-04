package com.xenrath.manusiabuah.ui.transaction.manage

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.ui.transaction.manage.tabs.history.TransactionHistoryManageFragment
import com.xenrath.manusiabuah.ui.transaction.manage.tabs.packed.TransactionPackedManageFragment
import com.xenrath.manusiabuah.ui.transaction.manage.tabs.sent.TransactionSentManageFragment
import kotlinx.android.synthetic.main.activity_transaction.*
import kotlinx.android.synthetic.main.toolbar_custom.*

class TransactionManageActivity : AppCompatActivity(), TransactionManageContract.View {

    lateinit var presenter: TransactionManagePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)

        presenter = TransactionManagePresenter(this)
    }

    @SuppressLint("SetTextI18n")
    override fun initActivity() {
        tv_title.text = "Pembelian Saya"

        val adapter = TransactionManageViewPagerAdapter(supportFragmentManager)

        adapter.addFragment(TransactionPackedManageFragment(), "Dikemas")
        adapter.addFragment(TransactionSentManageFragment(), "Dikirim")
        adapter.addFragment(TransactionHistoryManageFragment(), "Selesai")

        view_pager.adapter = adapter
        tabs.setupWithViewPager(view_pager)

        tabs.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_payment_24)
        tabs.getTabAt(1)!!.setIcon(R.drawable.ic_baseline_payment_24)
        tabs.getTabAt(2)!!.setIcon(R.drawable.ic_baseline_done_all_24)
    }

    override fun initListener() {
        iv_back.setOnClickListener {
            onBackPressed()
        }
    }
}