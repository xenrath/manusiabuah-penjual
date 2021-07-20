package com.xenrath.manusiabuah.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.ResponseProduct
import com.xenrath.manusiabuah.data.database.PrefManager
import com.xenrath.manusiabuah.data.database.model.DataProduct
import com.xenrath.manusiabuah.data.database.model.ResponseProductList
import kotlinx.android.synthetic.main.fragment_product.*

class HomeFragment : Fragment(), HomeContract.View {

    lateinit var presenter: HomePresenter
    lateinit var prefManager: PrefManager

    private lateinit var homeAdapter: HomeAdapter
    lateinit var progressBar: ProgressBar
    private lateinit var etSeach: EditText

    private lateinit var userId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_product, container, false)

        presenter = HomePresenter(this)
        prefManager = PrefManager(requireActivity())

        userId = prefManager.prefId.toString()

        initListener(view)

        return view
    }

    override fun onStart() {
        super.onStart()
        presenter.getProduct(userId, "Tebasan")
    }

    override fun initListener(view: View) {
        progressBar = view.findViewById(R.id.progress_bar)
        etSeach = view.findViewById(R.id.et_search)
        homeAdapter = HomeAdapter(requireActivity(), ArrayList())

        val rvProduct = view.findViewById<RecyclerView>(R.id.rv_product)
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rvProduct.adapter = homeAdapter
        rvProduct.layoutManager = layoutManager

        etSeach.setOnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                presenter.searchProduct(et_search.text.toString())
                true
            } else {
                false
            }
        }
    }

    override fun onResultList(responseProductList: ResponseProductList) {
        val dataProduct: List<DataProduct> = responseProductList.dataProduct
        homeAdapter.setData(dataProduct)
    }

    override fun onResultSearch(responseProduct: ResponseProduct) {
        val dataProduct: List<DataProduct> = responseProduct.product
        homeAdapter.setData(dataProduct)
    }

    override fun onLoading(loading: Boolean) {
        when(loading) {
            true -> progressBar.visibility = View.VISIBLE
            false -> progressBar.visibility = View.GONE
        }
    }

}