package com.xenrath.manusiabuah.ui.manage.bargain.tabs.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.database.PrefManager
import com.xenrath.manusiabuah.data.model.bargain.DataBargain
import com.xenrath.manusiabuah.data.model.bargain.ResponseBargainList
import com.xenrath.manusiabuah.ui.manage.bargain.ManageBargainAdapter
import com.xenrath.manusiabuah.utils.sweetalert.SweetAlertDialog

class ManageBargainHistoryFragment : Fragment(), ManageBargainHistoryContract.View {

    lateinit var prefManager: PrefManager
    lateinit var presenter: ManageBargainHistoryPresenter

    private lateinit var bargainAdapter: ManageBargainAdapter

    private lateinit var sLoading: SweetAlertDialog
    private lateinit var rvBargain: RecyclerView
    private lateinit var layoutEmpty: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_manage_bargain, container, false)

        prefManager = PrefManager(requireActivity())
        presenter = ManageBargainHistoryPresenter(this)

        initFragment(view)

        return view
    }

    override fun onStart() {
        super.onStart()
        presenter.bargainSellerHistory(prefManager.prefId.toString())
    }

    override fun initFragment(view: View) {
        layoutEmpty = view.findViewById(R.id.layout_empty)
        rvBargain = view.findViewById(R.id.rv_bargain)

        sLoading = SweetAlertDialog(requireActivity(), SweetAlertDialog.PROGRESS_TYPE)

        bargainAdapter = ManageBargainAdapter(requireActivity(), ArrayList())

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
        if (bargains.isEmpty()) {
            rvBargain.visibility = View.GONE
            layoutEmpty.visibility = View.VISIBLE
        } else {
            rvBargain.visibility = View.VISIBLE
            layoutEmpty.visibility = View.GONE
            bargainAdapter.setData(bargains)
        }
    }

}