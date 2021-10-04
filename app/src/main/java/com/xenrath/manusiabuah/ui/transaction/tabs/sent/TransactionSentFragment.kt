package com.xenrath.manusiabuah.ui.transaction.tabs.sent

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
import com.xenrath.manusiabuah.data.model.transaction.DataTransaction
import com.xenrath.manusiabuah.data.model.transaction.ResponseTransactionList
import com.xenrath.manusiabuah.ui.transaction.TransactionAdapter
import com.xenrath.manusiabuah.utils.sweetalert.SweetAlertDialog

class TransactionSentFragment : Fragment(), TransactionSentContract.View {

    lateinit var prefManager: PrefManager
    lateinit var presenter: TransactionSentPresenter

    private lateinit var adapterTransaction: TransactionAdapter

    private lateinit var sLoading: SweetAlertDialog

    private lateinit var rvTransaction: RecyclerView
    private lateinit var layoutEmpty: LinearLayout
    private lateinit var tvEmpty: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_transaction, container, false)

        prefManager = PrefManager(requireActivity())
        presenter = TransactionSentPresenter(this)

        initFragment(view)
        return view
    }

    override fun onStart() {
        super.onStart()
        presenter.transactionSent(prefManager.prefId)
    }

    override fun initFragment(view: View) {
        rvTransaction = view.findViewById(R.id.rv_transaction)
        layoutEmpty = view.findViewById(R.id.layout_empty)
        tvEmpty = view.findViewById(R.id.tv_empty)

        sLoading = SweetAlertDialog(requireActivity(), SweetAlertDialog.PROGRESS_TYPE)

        adapterTransaction = TransactionAdapter(requireActivity(), ArrayList())

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rvTransaction.adapter = adapterTransaction
        rvTransaction.layoutManager = layoutManager
    }

    override fun onLoading(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }

    override fun onResult(responseTransactionList: ResponseTransactionList) {
        val status: Boolean = responseTransactionList.status
        val message: String = responseTransactionList.message!!
        val transactions: List<DataTransaction> = responseTransactionList.transactions!!

        if (status) {
            rvTransaction.visibility = View.VISIBLE
            layoutEmpty.visibility = View.GONE
            adapterTransaction.setData(transactions)
        } else {
            rvTransaction.visibility = View.GONE
            layoutEmpty.visibility = View.VISIBLE
            tvEmpty.text = message
        }
    }

}