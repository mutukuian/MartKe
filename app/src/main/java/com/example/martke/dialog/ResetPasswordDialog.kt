package com.example.martke.dialog

import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.martke.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint


fun Fragment.setupBottomSheetDialog(
    onSendClick: (String) ->Unit
){
    val dialog = BottomSheetDialog(requireContext(),R.style.DialogStyle)
    val view = layoutInflater.inflate(R.layout.reset_password_dialog,null)
    dialog.setContentView(view)
    dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    dialog.show()


    val  email = view.findViewById<EditText>(R.id.edResetPassword)
    val  buttonSend = view.findViewById<Button>(R.id.btnSendResetPassword)
    val  buttonCancel = view.findViewById<Button>(R.id.buttonCancelResetPassword)

    buttonSend.setOnClickListener {
        val mail = email.text.toString().trim()
        onSendClick(mail)
        dialog.dismiss()
    }

    buttonCancel.setOnClickListener {
        dialog.dismiss()
    }
}