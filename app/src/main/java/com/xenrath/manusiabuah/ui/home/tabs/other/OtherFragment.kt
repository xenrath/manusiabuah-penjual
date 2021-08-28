package com.xenrath.manusiabuah.ui.home.tabs.other

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.database.PrefManager
import com.xenrath.manusiabuah.data.model.product.DataProduct
import com.xenrath.manusiabuah.data.model.product.ResponseProductList
import com.xenrath.manusiabuah.utils.sweetalert.SweetAlertDialog
import kotlinx.android.synthetic.main.fragment_home.*

class OtherFragment : Fragment(), OtherContract.View {

    lateinit var presenter: OtherPresenter
    lateinit var prefManager: PrefManager

    private lateinit var sLoading: SweetAlertDialog
    private lateinit var etSeach: EditText

    private lateinit var otherAdapter: OtherAdapter

    private lateinit var rvProduct: RecyclerView
    private lateinit var layoutEmptyProduct: LinearLayout
    private lateinit var layoutEmptyProductSearch: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        presenter = OtherPresenter(this)
        prefManager = PrefManager(requireActivity())

        initListener(view)

        return view
    }

    override fun onStart() {
        super.onStart()
        presenter.getProduct(prefManager.prefId.toString(), "Tebasan")
    }

    @SuppressLint("SetTextI18n")
    override fun initListener(view: View) {
        val ivBack = view.findViewById<ImageView>(R.id.iv_back)
        val tvTitle = view.findViewById<TextView>(R.id.tv_title)
        val ivHelp = view.findViewById<ImageView>(R.id.iv_help)

        rvProduct = view.findViewById(R.id.rv_product)
        layoutEmptyProduct = view.findViewById(R.id.layout_empty_product)
        layoutEmptyProductSearch = view.findViewById(R.id.layout_empty_product_search)
        etSeach = view.findViewById(R.id.et_search)

        sLoading = SweetAlertDialog(requireActivity(), SweetAlertDialog.PROGRESS_TYPE)

        tvTitle.text = "Home"
        ivBack.visibility = View.GONE
        ivHelp.visibility = View.GONE

        otherAdapter = OtherAdapter(requireActivity(), ArrayList())
        rvProduct.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2)
            adapter = otherAdapter
        }

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
        val products: List<DataProduct> = responseProductList.products!!
        if (products.isEmpty()) {
            rvProduct.visibility = View.GONE
            layoutEmptyProduct.visibility = View.VISIBLE
        } else {
            rvProduct.visibility = View.VISIBLE
            layoutEmptyProduct.visibility = View.GONE
            otherAdapter.setData(products)
        }
    }

    override fun onResultSearch(responseProductList: ResponseProductList) {
        val products: List<DataProduct> = responseProductList.products!!
        if (products.isEmpty()) {
            rvProduct.visibility = View.GONE
            layoutEmptyProductSearch.visibility = View.VISIBLE
        } else {
            rvProduct.visibility = View.VISIBLE
            layoutEmptyProductSearch.visibility = View.GONE
            otherAdapter.setData(products)
        }
    }

    override fun onLoading(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }

}