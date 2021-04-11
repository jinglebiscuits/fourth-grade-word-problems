package com.completewordproblems.fourthgrade.fragments

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class VocabularyDialogFragment(private val vocabularyDialogListener: VocabularyDialogListener) :
    DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val word = arguments?.getString("word")
        return AlertDialog.Builder(requireContext())
            .setTitle("Select the correct definition for: $word")
            .setPositiveButton("Submit"
            ) { _, _ -> vocabularyDialogListener.onSubmit() }
            .create()
    }

    fun setArguments(word: String) {

        // Supply num input as an argument.
        val args = Bundle()
        args.putString("word", word)
        this.arguments = args
    }

    interface VocabularyDialogListener {
        fun onSubmit()
    }

    companion object {
        const val TAG = "VOCABULARY_DIALOG_FRAGMENT"
    }
}