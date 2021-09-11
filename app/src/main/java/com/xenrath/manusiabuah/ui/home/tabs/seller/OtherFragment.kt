package com.xenrath.manusiabuah.ui.home.tabs.seller

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.database.PrefManager
import com.xenrath.manusiabuah.data.model.product.DataProduct
import com.xenrath.manusiabuah.data.model.product.ResponseProductList
import com.xenrath.manusiabuah.utils.sweetalert.SweetAlertDialog

class OtherFragment : Fragment(), OtherContract.View {

    lateinit var presenter: OtherPresenter
    lateinit var prefManager: PrefManager

    private lateinit var sLoading: SweetAlertDialog

    private lateinit var otherAdapter: OtherAdapter

    private lateinit var rvProduct: RecyclerView
    private lateinit var layoutSearch: LinearLayout
    private lateinit var layoutEmpty: LinearLayout
    private lateinit var tvEmpty: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_other, container, false)

        presenter = OtherPresenter(this)
        prefManager = PrefManager(requireActivity())

        initFragment(view)

        return view
    }

    override fun onStart() {
        super.onStart()
        presenter.getProduct(prefManager.prefId.toString(), "Tebasan")
    }

    @SuppressLint("SetTextI18n")
    override fun initFragment(view: View) {
        val etSearch = view.findViewById<EditText>(R.id.et_search)

        rvProduct = view.findViewById(R.id.rv_product)
        layoutSearch = view.findViewById(R.id.layout_search)
        layoutEmpty = view.findViewById(R.id.layout_empty)
        tvEmpty = view.findViewById(R.id.tv_empty)

        sLoading = SweetAlertDialog(requireActivity(), SweetAlertDialog.PROGRESS_TYPE)

        otherAdapter = OtherAdapter(requireActivity(), ArrayList())
        rvProduct.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2)
            adapter = otherAdapter
        }

        etSearch.setOnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                presenter.searchProduct(etSearch.text.toString())
                true
            } else {
                false
            }
        }
    }

    override fun onResultList(responseProductList: ResponseProductList) {
        val status: Boolean = responseProductList.status
        val message: String = responseProductList.message!!
        val products: List<DataProduct> = responseProductList.products!!

        if (status) {
            layoutSearch.visibility = View.VISIBLE
            rvProduct.visibility = View.VISIBLE
            layoutEmpty.visibility = View.GONE
            otherAdapter.setData(products)
        } else {
            layoutSearch.visibility = View.GONE
            rvProduct.visibility = View.GONE
            layoutEmpty.visibility = View.VISIBLE
            tvEmpty.text = message
        }
    }

    override fun onResultSearch(responseProductList: ResponseProductList) {
        val status: Boolean = responseProductList.status
        val message: String = responseProductList.message!!
        val products: List<DataProduct> = responseProductList.products!!

        if (status) {
            layoutSearch.visibility = View.VISIBLE
            rvProduct.visibility = View.VISIBLE
            layoutEmpty.visibility = View.GONE
            otherAdapter.setData(products)
        } else {
            layoutSearch.visibility = View.GONE
            rvProduct.visibility = View.GONE
            layoutEmpty.visibility = View.VISIBLE
            tvEmpty.text = message
        }
    }

    override fun onLoading(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }

}