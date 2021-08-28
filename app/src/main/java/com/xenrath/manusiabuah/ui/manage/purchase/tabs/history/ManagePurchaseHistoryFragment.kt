package com.xenrath.manusiabuah.ui.manage.purchase.tabs.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.database.PrefManager
import com.xenrath.manusiabuah.data.model.bargain.DataBargain
import com.xenrath.manusiabuah.data.model.bargain.ResponseBargainList
import com.xenrath.manusiabuah.ui.manage.bargain.ManageBargainAdapter
import com.xenrath.manusiabuah.utils.sweetalert.SweetAlertDialog

class ManagePurchaseHistoryFragment : Fragment(), ManagePurchaseHistoryContract.View {

    lateinit var prefManager: PrefManager
    lateinit var presenter: ManagePurchaseHistoryPresenter

    private lateinit var bargainAdapter: ManageBargainAdapter

    private lateinit var sLoading: SweetAlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_manage_bargain, container, false)

        prefManager = PrefManager(requireActivity())
        presenter = ManagePurchaseHistoryPresenter(this)

        initFragment(view)

        return view
    }

    override fun onStart() {
        super.onStart()
        presenter.getBargainHistory(prefManager.prefId.toString(), "Selesai")
    }

    override fun initFragment(view: View) {
        bargainAdapter = ManageBargainAdapter(requireActivity(), ArrayList())
        sLoading = SweetAlertDialog(requireActivity(), SweetAlertDialog.PROGRESS_TYPE)

        val rvBargain = view.findViewById<RecyclerView>(R.id.rv_bargain)
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rvBargain.adapter = bargainAdapter
        rvBargain.layoutManager = layoutManager
    }

    override fun onLoading(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }

    override fun onResult(responseBargainList: ResponseBargainList) {
        val bargains: List<DataBargain> = responseBargainList.bargains
        bargainAdapter.setData(bargains)
    }

}