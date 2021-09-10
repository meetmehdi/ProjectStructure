package com.android.framework.mvvm.ui.view.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.android.framework.mvvm.R
import com.android.framework.mvvm.data.model.LoginModel
import com.android.framework.mvvm.databinding.ActivityLoginBinding
import com.android.framework.mvvm.ui.viewmodel.LoginViewModel
import com.app.imagepickerlibrary.ImagePickerActivityClass
import com.app.imagepickerlibrary.ImagePickerBottomsheet
import com.app.imagepickerlibrary.bottomSheetActionCamera
import com.app.imagepickerlibrary.bottomSheetActionGallary
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity: AppCompatActivity(),
    ImagePickerBottomsheet.ItemClickListener, ImagePickerActivityClass.OnResult {

    private val viewModel:LoginViewModel by viewModels()
    private lateinit var loginBinding:ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        loginBinding.viewModel = viewModel
        loginBinding.loginModel = viewModel.loginModel
        loginBinding.lifecycleOwner = this

        viewModel.imagePicker = ImagePickerActivityClass(this, this, activityResultRegistry, activity = this)
        //set to true if you want all features(crop,rotate,zoomIn,zoomOut)
        //by Default it's value is set to false (only crop feature is enabled)
        viewModel.imagePicker.cropOptions(true)
    }

    override fun onItemClick(item: String?) {
        when {
            item.toString() == bottomSheetActionCamera -> {
                viewModel.imagePicker.takePhotoFromCamera()
            }
            item.toString() == bottomSheetActionGallary -> {
                viewModel.imagePicker.choosePhotoFromGallery()
            }
        }
    }

    //Override this method for customization of bottomsheet
    override fun doCustomisations(fragment: ImagePickerBottomsheet) {
        fragment.apply {
            //Customize button text
            setButtonText(
                cameraButtonText = "Select Camera",
                galleryButtonText = "Select Gallery",
                cancelButtonText = "Cancel"
            )

            //For more customization make a style in your styles xml and pass it to this method. (This will override above method result).
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                setTextAppearance(R.style.fontForNotificationLandingPage)
            }

            //Customize button text color
            setButtonColors(
                galleryButtonColor = ContextCompat.getColor(requireContext(), R.color.colorPrimary),
                cameraButtonColor = ContextCompat.getColor(requireContext(), R.color.colorPrimary),
                cancelButtonColor = ContextCompat.getColor(requireContext(), R.color.color_cancel_text)
            )

            //To customize bottomsheet style
            setBottomSheetBackgroundStyle(R.drawable.drawable_bottom_sheet_dialog)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.imagePicker.onActivityResult(requestCode, resultCode, data)
    }

    override fun returnString(item: Uri?) {
        print("OnClick Image ${item?.path.toString()}")
    }
}