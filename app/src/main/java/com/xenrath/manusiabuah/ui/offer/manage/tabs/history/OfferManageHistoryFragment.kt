package com.xenrath.manusiabuah.ui.offer.manage.tabs.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.database.PrefManager
import com.xenrath.manusiabuah.data.model.offer.DataOffer
import com.xenrath.manusiabuah.data.model.offer.ResponseOfferList
import com.xenrath.manusiabuah.ui.offer.manage.OfferManageAdapter
import com.xenrath.manusiabuah.utils.sweetalert.SweetAlertDialog

class OfferManageHistoryFragment : Fragment(), OfferManageHistoryContract.View {

    lateinit var prefManager: PrefManager
    lateinit var presenterOffer: OfferManageHistoryPresenter

    private lateinit var adapterOffer: OfferManageAdapter

    private lateinit var sLoading: SweetAlertDialog

    private lateinit var rvBargain: RecyclerView
    private lateinit var layoutEmpty: LinearLayout
    private lateinit var tvEmpty: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_offer, container, false)

        prefManager = PrefManager(requireActivity())
        presenterOffer = OfferManageHistoryPresenter(this)

        initFragment(view)

        return view
    }

    override fun onStart() {
        super.onStart()
        presenterOffer.offerHistoryManage(prefManager.prefId)
    }

    override fun initFragment(view: View) {
        layoutEmpty = view.findViewById(R.id.layout_empty)
        rvBargain = view.findViewById(R.id.rv_bargain)
        tvEmpty = view.findViewById(R.id.tv_empty)

        sLoading = SweetAlertDialog(requireActivity(), SweetAlertDialog.PROGRESS_TYPE)

        adapterOffer = OfferManageAdapter(requireActivity(), ArrayList())

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rvBargain.adapter = adapterOffer
        rvBargain.layoutManager = layoutManager
    }

    override fun onLoading(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }

    override fun onResult(responseOfferList: ResponseOfferList) {
        val status: Boolean = responseOfferList.status
        val message: String = responseOfferList.message!!
        val offers: List<DataOffer> = responseOfferList.offers!!

        if (status) {
            rvBargain.visibility = View.VISIBLE
            layoutEmpty.visibility = View.GONE
            adapterOffer.setData(offers)
        } else {
            rvBargain.visibility = View.GONE
            layoutEmpty.visibility = View.VISIBLE
            tvEmpty.text = message
        }
    }

}