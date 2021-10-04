package com.xenrath.manusiabuah.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.ui.home.tabs.list.ProductFragment
import com.xenrath.manusiabuah.ui.home.tabs.sell.OtherFragment

class HomeFragment : Fragment(), HomeContract.View {

    lateinit var presenter: HomePresenter

    lateinit var tvTitle: TextView
    private lateinit var ivBack: ImageView
    private lateinit var viewPager: ViewPager
    lateinit var tabs: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        presenter = HomePresenter(this)

        initFragment(view)

        return view
    }

    @SuppressLint("SetTextI18n")
    override fun initFragment(view: View) {
        tvTitle = view.findViewById(R.id.tv_title)
        ivBack = view.findViewById(R.id.iv_back)
        viewPager = view.findViewById(R.id.view_pager)
        tabs = view.findViewById(R.id.tabs)

        tvTitle.text = "Home"
        ivBack.visibility = View.GONE

        val adapter = HomeViewPagerAdapter(requireActivity().supportFragmentManager)
        adapter.addFragment(OtherFragment(), "Beli Produk")
        adapter.addFragment(ProductFragment(), "Produk Saya")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }
}