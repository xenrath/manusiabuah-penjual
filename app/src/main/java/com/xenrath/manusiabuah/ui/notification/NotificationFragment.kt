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
import com.xenrath.manusiabuah.data.model.offer.DataOffer
import com.xenrath.manusiabuah.data.model.offer.ResponseOfferList
import com.xenrath.manusiabuah.ui.offer.OfferActivity
import com.xenrath.manusiabuah.ui.manage.offer.OfferManageActivity
import com.xenrath.manusiabuah.ui.transaction.TransactionActivity
import com.xenrath.manusiabuah.utils.sweetalert.SweetAlertDialog

class NotificationFragment : Fragment(), NotificationContract.View {

    lateinit var prefManager: PrefManager
    lateinit var presenter: NotificationPresenter

    lateinit var tvTitle: TextView
    private lateinit var ivBack: ImageView

    private lateinit var btnOffer: RelativeLayout
    private lateinit var btnOfferManage: RelativeLayout
    private lateinit var btnPurchase: RelativeLayout
    private lateinit var btnPurchaseManage: RelativeLayout

    private lateinit var layoutCountOffer: LinearLayout
    private lateinit var layoutCountOfferManage: LinearLayout
    private lateinit var layoutCountPurchase: LinearLayout
    private lateinit var layoutCountPurchaseManage: LinearLayout

    private lateinit var tvCountOffer: TextView
    private lateinit var tvCountOfferManage: TextView
    private lateinit var tvCountPurchase: TextView
    private lateinit var tvCountPurchaseManage: TextView

    lateinit var offer: DataOffer

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

        btnOffer = view.findViewById(R.id.btn_offer)
        btnOfferManage = view.findViewById(R.id.btn_offer_manage)
        btnPurchase = view.findViewById(R.id.btn_purchase)
        btnPurchaseManage = view.findViewById(R.id.btn_purchase_manage)

        layoutCountOffer = view.findViewById(R.id.layout_count_offer)
        layoutCountOfferManage = view.findViewById(R.id.layout_count_offer_manage)
        layoutCountPurchase = view.findViewById(R.id.layout_count_purchase)
        layoutCountPurchaseManage = view.findViewById(R.id.layout_count_purchase_manage)

        tvCountOffer = view.findViewById(R.id.tv_count_offer)
        tvCountOfferManage = view.findViewById(R.id.tv_count_offer_manage)
        tvCountPurchase = view.findViewById(R.id.tv_count_purchase)
        tvCountPurchaseManage = view.findViewById(R.id.tv_count_purchase_manage)

        sLoading = SweetAlertDialog(requireActivity(), SweetAlertDialog.PROGRESS_TYPE)

        tvTitle.text = "Notification"
        ivBack.visibility = View.GONE

        btnOffer.setOnClickListener {
            startActivity(Intent(requireActivity(), OfferActivity::class.java))
        }
        btnOfferManage.setOnClickListener {
            startActivity(Intent(requireActivity(), OfferManageActivity::class.java))
        }
        btnPurchase.setOnClickListener {
            startActivity(Intent(requireActivity(), TransactionActivity::class.java))
        }
        btnPurchaseManage.setOnClickListener {

        }
    }

    override fun onLoading(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }

    override fun onResultMyBargain(responseOfferList: ResponseOfferList) {
        val status = responseOfferList.status
        val bargains = responseOfferList.offers!!

        if (status) {
            layoutCountOffer.visibility = View.VISIBLE
            tvCountOffer.text = bargains.size.toString()
        } else {
            layoutCountOffer.visibility = View.GONE
        }
    }

    override fun onResultManageBargain(responseOfferList: ResponseOfferList) {
        val status = responseOfferList.status
        val bargains = responseOfferList.offers!!

        if (status) {
            layoutCountOfferManage.visibility = View.VISIBLE
            tvCountOfferManage.text = bargains.size.toString()
        } else {
            layoutCountOfferManage.visibility = View.GONE
        }
    }

    override fun onResultMyPurchase(responseOfferList: ResponseOfferList) {

    }

    override fun onResultManagePurchase(responseOfferList: ResponseOfferList) {

    }
}