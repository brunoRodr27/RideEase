package com.example.rideease.util

import androidx.fragment.app.Fragment
import com.example.rideease.R
import com.example.rideease.databinding.BottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

fun Fragment.showButtomSheet(
    titleDialog: String = "Atenção",
    titleButtom: String = "Confirmar",
    message: String,
    onClick: () -> Unit = {}
) {
    val buttomSheetDialog = BottomSheetDialog(requireContext(), R.style.ThemeButtonSheetDialog)
    val buttomSheetBinding: BottomSheetBinding =
        BottomSheetBinding.inflate(layoutInflater, null, false)

    buttomSheetBinding.tvTitle.text = titleDialog
    buttomSheetBinding.btOK.text = titleButtom
    buttomSheetBinding.tvMessage.text = message
    buttomSheetBinding.btOK.setOnClickListener {
        onClick()
        buttomSheetDialog.dismiss()
    }
    buttomSheetDialog.setContentView(buttomSheetBinding.root)
    buttomSheetDialog.show()
}