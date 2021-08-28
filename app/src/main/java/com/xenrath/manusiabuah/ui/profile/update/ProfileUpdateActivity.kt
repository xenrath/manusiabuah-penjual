package com.xenrath.manusiabuah.ui.profile.update

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.database.PrefManager
import com.xenrath.manusiabuah.data.model.user.ResponseUserUpdate
import com.xenrath.manusiabuah.utils.FileUtils
import com.xenrath.manusiabuah.utils.GalleryHelper
import com.xenrath.manusiabuah.utils.GlideHelper
import com.xenrath.manusiabuah.utils.sweetalert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_profile_update.*
import kotlinx.android.synthetic.main.toolbar_custom.*

class ProfileUpdateActivity : AppCompatActivity(), ProfileUpdateContract.View {

    lateinit var prefManager: PrefManager
    lateinit var presenter: ProfileUpdatePresenter

    private var uri: Uri? = null
    private var pickImage = 1

    private lateinit var sLoading: SweetAlertDialog
    private lateinit var sSuccess: SweetAlertDialog
    private lateinit var sError: SweetAlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_update)

        prefManager = PrefManager(this)
        presenter = ProfileUpdatePresenter(this)

    }

    override fun onStart() {
        super.onStart()

        et_name.setText(prefManager.prefName)
        et_email.setText(prefManager.prefEmail)
        et_phone.setText(prefManager.prefPhone)
        et_address.setText(prefManager.prefAddress)

        GlideHelper.setImage(this, prefManager.prefImage, iv_user_image)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImage && resultCode == Activity.RESULT_OK) {
            uri = data!!.data
            iv_user_image.setImageURI(uri)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initActivity() {
        tv_title.text = "Perbarui Profil"

        sLoading = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError = SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal!")
    }

    override fun initListener() {
        iv_back.setOnClickListener {
            onBackPressed()
        }
        iv_user_image.setOnClickListener {
            if (GalleryHelper.permissionGallery(this, this, pickImage)) {
                GalleryHelper.openGallery(this)
            }
        }

        btn_save.setOnClickListener {
            val name = et_name.text
            val email = et_email.text
            val phone = et_phone.text
            val address = et_address.text
            when {
                name.isEmpty() -> {
                    validationError(et_name, "Nama lengkap tidak boleh kosong!")
                }
                email.isEmpty() -> {
                    validationError(et_email, "Email tidak boleh kosong!")
                }
                phone.isEmpty() -> {
                    validationError(et_phone, "Nomor telepon tidak boleh kosong!")
                }
                address.isEmpty() -> {
                    validationError(et_address, "Alamat tidak boleh kosong!")
                }
            }
            presenter.updateProfile(
                prefManager.prefId,
                name.toString(),
                email.toString(),
                phone.toString(),
                address.toString(),
                FileUtils.getFile(this, uri)
            )
        }
    }

    override fun onLoading(loading: Boolean) {
        when (loading) {
            true -> sLoading.show()
            false -> sLoading.dismiss()
        }
    }

    override fun onResult(responseUserUpdate: ResponseUserUpdate) {
        if (responseUserUpdate.status) {
            showAlertSuccess(responseUserUpdate.message)
        } else {
            showAlertError(responseUserUpdate.message)
        }
    }

    override fun showAlertSuccess(message: String) {
        sSuccess
            .setContentText(message)
            .setConfirmText("OK")
            .setConfirmClickListener {
                it.dismissWithAnimation()
                finish()
            }
            .show()
    }

    override fun showAlertError(message: String) {
        sError
            .setContentText(message)
            .setConfirmText("OK")
            .setConfirmClickListener {
                it.dismissWithAnimation()
            }
            .show()
    }

    override fun validationError(editText: EditText, message: String) {
        editText.error = message
        editText.requestFocus()
    }
}