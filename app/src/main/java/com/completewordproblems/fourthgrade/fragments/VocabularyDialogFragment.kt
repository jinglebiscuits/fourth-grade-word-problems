package com.completewordproblems.fourthgrade.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.completewordproblems.fourthgrade.models.KeyWord

class VocabularyDialogFragment(
    private val vocabularyDialogListener: VocabularyDialogListener,
    private val keyWord: KeyWord
) :
    DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Select the correct definition for: ${keyWord.keyWord}")
            .setPositiveButton(
                "Submit"
            ) { _, _ -> vocabularyDialogListener.onSubmit(keyWord) }
            .create()
    }

    interface VocabularyDialogListener {
        fun onSubmit(keyWord: KeyWord)
    }

    companion object {
        const val TAG = "VOCABULARY_DIALOG_FRAGMENT"
    }
}