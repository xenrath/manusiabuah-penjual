package com.xenrath.manusiabuah.ui.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.database.PrefManager
import com.xenrath.manusiabuah.ui.HistoryActivity
import com.xenrath.manusiabuah.ui.login.LoginActivity
import com.xenrath.manusiabuah.ui.product.ProductActivity

class ProfileFragment : Fragment(), ProfileContract.View {

    lateinit var prefManager: PrefManager
    lateinit var presenter: ProfilePresenter

    lateinit var tvTitle: TextView
    private lateinit var ivBack: ImageView
    private lateinit var ivHelp: ImageView

    private lateinit var btnLogout: TextView
    private lateinit var tvName: TextView
    private lateinit var btnUpdateProfile: TextView
    private lateinit var btnHistoryTransaction: TextView
    private lateinit var btnMyProduct: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)

        prefManager = PrefManager(requireActivity())
        presenter = ProfilePresenter(this)

        initFragment(view)

        presenter.doLogin(prefManager)

        return view
    }

    @SuppressLint("SetTextI18n")
    override fun initFragment(view: View) {
        tvTitle = view.findViewById(R.id.tv_title)
        ivBack = view.findViewById(R.id.iv_back)
        ivHelp = view.findViewById(R.id.iv_help)
        btnLogout = view.findViewById(R.id.btn_logout)
        tvName = view.findViewById(R.id.tv_name)
        btnUpdateProfile = view.findViewById(R.id.btn_update_profile)
        btnHistoryTransaction = view.findViewById(R.id.btn_history_transaction)
        btnMyProduct = view.findViewById(R.id.btn_my_product)

        tvTitle.text = "Profile"
        ivBack.visibility = View.GONE
        ivHelp.setOnClickListener {

        }
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
    }

    override fun onResultLogout() {
        requireActivity().finish()
        startActivity(Intent(requireActivity(), LoginActivity::class.java))
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireActivity().applicationContext, message, Toast.LENGTH_SHORT).show()
    }

}