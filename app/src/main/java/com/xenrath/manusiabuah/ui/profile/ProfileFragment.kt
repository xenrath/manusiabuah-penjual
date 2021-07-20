package com.xenrath.manusiabuah.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.database.PrefManager
import com.xenrath.manusiabuah.ui.HistoryActivity
import com.xenrath.manusiabuah.ui.product.ProductActivity

class ProfileFragment: Fragment(), ProfileContract.View {

    lateinit var prefManager: PrefManager
    lateinit var presenter: ProfilePresenter

    private lateinit var btnLogout: TextView
    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var btnUpdateProfile: CardView
    private lateinit var btnHistoryTransaction: CardView
    private lateinit var btnMyProduct: CardView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)

        prefManager = PrefManager(requireActivity())
        presenter = ProfilePresenter(this)

        initListener(view)

        presenter.doLogin(prefManager)

        return view
    }

    override fun initListener(view: View) {
        btnLogout = view.findViewById(R.id.btn_logout)
        tvName = view.findViewById(R.id.tv_name)
        tvEmail = view.findViewById(R.id.tv_email)
        btnUpdateProfile = view.findViewById(R.id.btn_update_profile)
        btnHistoryTransaction = view.findViewById(R.id.btn_history_transaction)
        btnMyProduct = view.findViewById(R.id.btn_my_product)

        btnLogout.setOnClickListener {
            presenter.doLogout(prefManager)
        }
        btnUpdateProfile.setOnClickListener {

        }
        btnHistoryTransaction.setOnClickListener {
            startActivity(Intent(requireActivity(), HistoryActivity::class.java))
        }
        btnMyProduct.setOnClickListener {
            startActivity(Intent(requireActivity(), ProductActivity::class.java))
        }
    }

    override fun onResultLogin(prefManager: PrefManager) {
        tvName.text = prefManager.prefName
        tvEmail.text = prefManager.prefEmail
    }

    override fun onResultLogout() {
        requireActivity().finish()
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireActivity().applicationContext, message, Toast.LENGTH_SHORT).show()
    }

}