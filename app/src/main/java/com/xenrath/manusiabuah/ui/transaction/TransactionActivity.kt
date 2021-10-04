package com.xenrath.manusiabuah.ui.transaction

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.ui.transaction.tabs.history.TransactionHistoryFragment
import com.xenrath.manusiabuah.ui.transaction.tabs.packed.TransactionPackedFragment
import com.xenrath.manusiabuah.ui.transaction.tabs.paid.TransactionPaidFragment
import com.xenrath.manusiabuah.ui.transaction.tabs.sent.TransactionSentFragment
import kotlinx.android.synthetic.main.activity_transaction.*
import kotlinx.android.synthetic.main.toolbar_custom.*

class TransactionActivity : AppCompatActivity(), TransactionContract.View {

    lateinit var presenter: TransactionPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)

        presenter = TransactionPresenter(this)
    }

    @SuppressLint("SetTextI18n")
    override fun initActivity() {
        tv_title.text = "Pembelian Saya"

        val adapter = TransactionViewPagerAdapter(supportFragmentManager)

        adapter.addFragment(TransactionPaidFragment(), "")
        adapter.addFragment(TransactionPackedFragment(), "")
        adapter.addFragment(TransactionSentFragment(), "")
        adapter.addFragment(TransactionHistoryFragment(), "")

        view_pager.adapter = adapter
        tabs.setupWithViewPager(view_pager)

        tabs.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_payment_24)
        tabs.getTabAt(1)!!.setIcon(R.drawable.ic_baseline_payment_24)
        tabs.getTabAt(2)!!.setIcon(R.drawable.ic_baseline_done_all_24)
        tabs.getTabAt(3)!!.setIcon(R.drawable.ic_baseline_payment_24)
    }

    override fun initListener() {
        iv_back.setOnClickListener {
            onBackPressed()
        }
    }
}