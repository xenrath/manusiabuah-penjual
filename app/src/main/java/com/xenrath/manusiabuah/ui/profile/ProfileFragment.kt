package com.xenrath.manusiabuah.ui.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.database.PrefManager
import com.xenrath.manusiabuah.ui.account.AccountActivity
import com.xenrath.manusiabuah.ui.login.LoginActivity
import com.xenrath.manusiabuah.utils.sweetalert.SweetAlertDialog

class ProfileFragment : Fragment(), ProfileContract.View {

    lateinit var prefManager: PrefManager
    lateinit var presenter: ProfilePresenter

    private lateinit var sLoading: SweetAlertDialog
    private lateinit var sSuccess: SweetAlertDialog
    private lateinit var sError: SweetAlertDialog
    private lateinit var sAlert: SweetAlertDialog

    lateinit var tvTitle: TextView
    private lateinit var ivBack: ImageView

    private lateinit var tvName: TextView
    private lateinit var btnUpdateProfile: TextView
    private lateinit var btnAccount: TextView
    private lateinit var btnLogout: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)

        presenter =  ProfilePresenter(this)
        prefManager = PrefManager(requireActivity())

        initFragment(view)

        return view
    }

    override fun onStart() {
        super.onStart()
        presenter.doLogin(prefManager)
    }

    @SuppressLint("SetTextI18n")
    override fun initFragment(view: View) {
        tvTitle = view.findViewById(R.id.tv_title)
        ivBack = view.findViewById(R.id.iv_back)
        tvName = view.findViewById(R.id.tv_name)
        btnUpdateProfile = view.findViewById(R.id.btn_update_profile)
        btnAccount = view.findViewById(R.id.btn_account)
        btnLogout = view.findViewById(R.id.btn_logout)

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

        tvTitle.text = "Profile"
        ivBack.visibility = View.GONE
        btnLogout.setOnClickListener {
            showAlert()
        }
        btnUpdateProfile.setOnClickListener {

        }
        btnAccount.setOnClickListener {
            startActivity(Intent(requireActivity(), AccountActivity::class.java))
        }
    }

    override fun onResultLogin(prefManager: PrefManager) {
        tvName.text = prefManager.prefName
    }

    override fun onResultLogout() {
        showAlertSuccess("Berhasil keluar aplikasi")
        requireActivity().finish()
        startActivity(Intent(requireActivity(), LoginActivity::class.java))
    }

    override fun showAlertSuccess(message: String) {
        sSuccess
            .setContentText(message)
            .setConfirmText("OK")
            .setConfirmClickListener {
                it.dismissWithAnimation()
            }
            .show()
    }

    override fun showAlert() {
        sAlert
            .setContentText("Yakin keluar aplikasi?")
            .setConfirmText("Ya, keluar")
            .setConfirmClickListener {
                it.dismissWithAnimation()
                presenter.doLogout(prefManager)
            }
            .setCancelText("Batal")
            .setCancelClickListener {
                it.dismiss()
            }
            .show()
    }
}