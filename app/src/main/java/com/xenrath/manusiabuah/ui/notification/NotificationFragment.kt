package com.xenrath.manusiabuah.ui.notification

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.database.PrefManager
import com.xenrath.manusiabuah.data.model.bargain.DataBargain
import com.xenrath.manusiabuah.data.model.bargain.ResponseBargainList
import com.xenrath.manusiabuah.ui.bargain.MyBargainActivity
import com.xenrath.manusiabuah.ui.manage.bargain.ManageBargainActivity
import com.xenrath.manusiabuah.utils.sweetalert.SweetAlertDialog

class NotificationFragment : Fragment(), NotificationContract.View {

    lateinit var prefManager: PrefManager
    lateinit var presenter: NotificationPresenter

    lateinit var tvTitle: TextView
    private lateinit var ivBack: ImageView

    lateinit var btnMyBargain: RelativeLayout
    private lateinit var btnManageBargain: RelativeLayout
    private lateinit var btnMyPurchase: RelativeLayout
    private lateinit var btnManagePurchase: RelativeLayout

    private lateinit var layoutCountMyBargain: LinearLayout
    private lateinit var layoutCountManageBargain: LinearLayout
    private lateinit var layoutCountMyPurchase: LinearLayout
    private lateinit var layoutCountManagePurchase: LinearLayout

    private lateinit var tvCountMyBargain: TextView
    private lateinit var tvCountManageBargain: TextView
    private lateinit var tvCountMyPurchase: TextView
    private lateinit var tvCountManagePurchase: TextView

    lateinit var bargain: DataBargain

    private lateinit var sLoading: SweetAlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_notification, container, false)

        prefManager = PrefManager(requireActivity())
        presenter = NotificationPresenter(this)

        initFragment(view)

        return view
    }

    @SuppressLint("SetTextI18n")
    override fun initFragment(view: View) {
        tvTitle = view.findViewById(R.id.tv_title)
        ivBack = view.findViewById(R.id.iv_back)

        btnMyBargain = view.findViewById(R.id.btn_my_bargain)
        btnManageBargain = view.findViewById(R.id.btn_manage_bargain)
        btnMyPurchase = view.findViewById(R.id.btn_my_purchase)
        btnManagePurchase = view.findViewById(R.id.btn_manage_purchase)

        layoutCountMyBargain = view.findViewById(R.id.layout_count_my_bargain)
        layoutCountManageBargain = view.findViewById(R.id.layout_count_manage_bargain)
        layoutCountMyPurchase = view.findViewById(R.id.layout_count_my_purchase)
        layoutCountManagePurchase = view.findViewById(R.id.layout_count_manage_purchase)

        tvCountMyBargain = view.findViewById(R.id.tv_count_my_bargain)
        tvCountManageBargain = view.findViewById(R.id.tv_count_manage_bargain)
        tvCountMyPurchase = view.findViewById(R.id.tv_count_my_purchase)
        tvCountManagePurchase = view.findViewById(R.id.tv_count_manage_purchase)

        sLoading = SweetAlertDialog(requireActivity(), SweetAlertDialog.PROGRESS_TYPE)

        tvTitle.text = "Notification"
        ivBack.visibility = View.GONE

        btnMyBargain.setOnClickListener {
            startActivity(Intent(requireActivity(), MyBargainActivity::class.java))
        }
        btnManageBargain.setOnClickListener {
            startActivity(Intent(requireActivity(), ManageBargainActivity::class.java))
        }
        btnMyPurchase.setOnClickListener {

        }
        btnManagePurchase.setOnClickListener {

        }
    }

    override fun onLoading(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }

    override fun onResultMyBargain(responseBargainList: ResponseBargainList) {
        val bargains = responseBargainList.bargains

        if (bargains.isEmpty()) {
            layoutCountMyBargain.visibility = View.GONE
        } else {
            layoutCountMyBargain.visibility = View.VISIBLE
            tvCountMyBargain.text = bargains.size.toString()
        }
    }

    override fun onResultManageBargain(responseBargainList: ResponseBargainList) {
        val bargains = responseBargainList.bargains

        if (bargains.isEmpty()) {
            layoutCountManageBargain.visibility = View.GONE
        } else {
            layoutCountManageBargain.visibility = View.VISIBLE
            tvCountManagePurchase.text = bargains.size.toString()
        }
    }

    override fun onResultMyPurchase(responseBargainList: ResponseBargainList) {

    }

    override fun onResultManagePurchase(responseBargainList: ResponseBargainList) {

    }
}