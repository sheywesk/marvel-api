package com.sheywesk.marvel_api.presentation.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.sheywesk.marvel_api.R

class PurchaseConfirmationDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage("TESTE")
            .setPositiveButton("OK") { _,_ -> }
            .create()

    companion object {
        const val TAG = "PurchaseConfirmationDialog"
    }
}