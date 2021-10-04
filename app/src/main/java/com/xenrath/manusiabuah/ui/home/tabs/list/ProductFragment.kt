package com.xenrath.manusiabuah.ui.home.tabs.list

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.database.PrefManager
import com.xenrath.manusiabuah.data.model.account.ResponseAccountList
import com.xenrath.manusiabuah.data.model.product.DataProduct
import com.xenrath.manusiabuah.data.model.product.ResponseProductList
import com.xenrath.manusiabuah.data.model.product.ResponseProductUpdate
import com.xenrath.manusiabuah.ui.account.AccountActivity
import com.xenrath.manusiabuah.ui.home.tabs.list.create.ProductCreateActivity
import com.xenrath.manusiabuah.ui.home.tabs.list.update.ProductUpdateActivity
import com.xenrath.manusiabuah.utils.GlideHelper
import com.xenrath.manusiabuah.utils.MapsHelper
import com.xenrath.manusiabuah.utils.sweetalert.SweetAlertDialog
import kotlinx.android.synthetic.main.dialog_product.view.*

class ProductFragment : Fragment(), ProductContract.View, OnMapReadyCallback {

    lateinit var prefManager: PrefManager
    lateinit var presenter: ProductPresenter

    private lateinit var sLoading: SweetAlertDialog
    private lateinit var sSuccess: SweetAlertDialog
    private lateinit var sError: SweetAlertDialog
    private lateinit var sAlert: SweetAlertDialog

    private lateinit var productAdapter: ProductAdapter
    lateinit var product: DataProduct

    private lateinit var rvProduct: RecyclerView
    lateinit var layoutSearch: LinearLayout
    private lateinit var layoutEmpty: LinearLayout
    private lateinit var tvEmpty: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_product, container, false)

        presenter = ProductPresenter(this)
        prefManager = PrefManager(requireActivity())

        initFragment(view)

        return view
    }

    override fun onStart() {
        super.onStart()
        presenter.productList(prefManager.prefId)
    }

    @SuppressLint("SetTextI18n")
    override fun initFragment(view: View) {
        val etSearch = view.findViewById<EditText>(R.id.et_search)
        val fab = view.findViewById<RelativeLayout>(R.id.fab)

        rvProduct = view.findViewById(R.id.rv_product)
        layoutSearch = view.findViewById(R.id.layout_search)
        layoutEmpty = view.findViewById(R.id.layout_empty)
        tvEmpty = view.findViewById(R.id.tv_empty)

        sLoading = SweetAlertDialog(requireActivity(), SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = SweetAlertDialog(
            requireActivity(),
            SweetAlertDialog.SUCCESS_TYPE
        ).setTitleText("Berhasil")
        sError =
            SweetAlertDialog(requireActivity(), SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal!")
        sAlert = SweetAlertDialog(
            requireActivity(),
            SweetAlertDialog.WARNING_TYPE
        ).setTitleText("Perhatian!")

        MapsHelper.permissionMap(requireActivity(), requireActivity())

        val layoutManager = LinearLayoutManager(requireActivity())
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        productAdapter = ProductAdapter(requireActivity(), arrayListOf())
        { dataProduct: DataProduct, position: Int, type: String ->
            product = dataProduct
            when (type) {
                "update" -> startActivity(
                    Intent(
                        requireActivity(),
                        ProductUpdateActivity::class.java
                    )
                )
                "delete" -> showAlertDelete(dataProduct, position)
                "detail" -> showDialogDetail(dataProduct, position)
            }
        }

        rvProduct.adapter = productAdapter
        rvProduct.layoutManager = layoutManager

        etSearch.setOnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                presenter.searchProduct(etSearch.text.toString())
                true
            } else {
                false
            }
        }

        fab.setOnClickListener {
            presenter.accountList(prefManager.prefId)
        }
    }

    override fun onLoading(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
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
            productAdapter.setData(products)
        } else {
            layoutSearch.visibility = View.GONE
            rvProduct.visibility = View.GONE
            layoutEmpty.visibility = View.VISIBLE
            tvEmpty.text = message
        }
    }

    override fun onResultDelete(responseProductUpdate: ResponseProductUpdate) {
        val status: Boolean = responseProductUpdate.status
        val message: String = responseProductUpdate.message!!

        if (status) {
            showAlertSuccess(message)
        } else {
            showAlertError(message)
        }
    }

    override fun onResultSearch(responseProductList: ResponseProductList) {
        val status: Boolean = responseProductList.status
        val message: String = responseProductList.message!!

        if (status) {
            val products: List<DataProduct> = responseProductList.products!!

            layoutSearch.visibility = View.VISIBLE
            rvProduct.visibility = View.VISIBLE
            layoutEmpty.visibility = View.GONE
            productAdapter.setData(products)
        } else {
            layoutSearch.visibility = View.GONE
            rvProduct.visibility = View.GONE
            layoutEmpty.visibility = View.VISIBLE
            tvEmpty.text = message
        }
    }

    override fun onResultAccount(responseAccountList: ResponseAccountList) {
        val status: Boolean = responseAccountList.status
        val message: String = responseAccountList.message!!

        if (status) {
            startActivity(Intent(requireActivity(), ProductCreateActivity::class.java))
        } else {
            showAlertAccount(message)
        }
    }

    override fun showAlertSuccess(message: String) {
        sSuccess
            .setContentText(message)
            .setConfirmText("OK")
            .setConfirmClickListener {
                it.dismissWithAnimation()
                requireActivity().finish()
            }
            .show()
    }

    override fun showAlertError(message: String) {
        sError
            .setContentText(message)
            .setConfirmText("OK")
            .setConfirmClickListener {
                it.dismiss()
            }
            .show()
    }

    override fun showAlertAccount(message: String) {
        sAlert
            .setContentText("$message\nTambahkan sekarang?")
            .setConfirmText("Lanjutkan")
            .setConfirmClickListener {
                it.dismissWithAnimation()
                startActivity(Intent(requireActivity(), AccountActivity::class.java))
            }
            .setCancelText("Batal")
            .setCancelClickListener {
                it.dismiss()
            }
            .show()
        sAlert.setCancelable(true)
    }

    override fun showAlertDelete(dataProduct: DataProduct, position: Int) {
        sAlert
            .setContentText("Yakin menghapus produk?")
            .setConfirmText("Hapus")
            .setConfirmClickListener {
                presenter.deleteProduct(dataProduct.id!!)
                productAdapter.removeProduct(position)
                it.dismissWithAnimation()
            }
            .setCancelText("Batal")
            .setCancelClickListener {
                it.dismiss()
            }
            .show()
        sAlert.setCancelable(true)
    }

    @SuppressLint("InflateParams")
    override fun showDialogDetail(dataProduct: DataProduct, position: Int) {
        val dialog = BottomSheetDialog(requireActivity())
        val view = layoutInflater.inflate(R.layout.dialog_product, null)

        GlideHelper.setImage(requireActivity(), dataProduct.image!!, view.iv_product)

        view.tv_name.text = dataProduct.name
        view.tv_address.text = dataProduct.address

        val mapFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng(product.latitude!!.toDouble(), product.longitude!!.toDouble())
        googleMap.addMarker(MarkerOptions().position(latLng).title(product.name))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f))
    }
}