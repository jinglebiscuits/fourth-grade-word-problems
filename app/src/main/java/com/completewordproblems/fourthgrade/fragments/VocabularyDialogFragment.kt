package com.completewordproblems.fourthgrade.fragments

import android.app.Dialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.completewordproblems.fourthgrade.R
import com.completewordproblems.fourthgrade.models.FourthGradeDictionary
import com.completewordproblems.fourthgrade.models.KeyWord

class VocabularyDialogFragment(
    private val vocabularyDialogListener: VocabularyDialogListener,
    private val keyWord: KeyWord
) :
    DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val customView = layoutInflater.inflate(R.layout.dialog_vocabulary_quiz, null)
        val definitionOptions: ListView =
            customView.findViewById(R.id.definition_options)
        var options: MutableList<String> = arrayListOf()
        FourthGradeDictionary.getWord(keyWord.keyWord)
            ?.let { options.addAll(it.incorrectDefinitions) }
        FourthGradeDictionary.getWord(keyWord.keyWord)?.let { options.add(it.correctDefinition) }
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(), android.R.layout.simple_list_item_single_choice,
            options
        )
        definitionOptions.adapter = adapter
        return AlertDialog.Builder(requireContext())
            .setTitle("Select the correct definition for: ${keyWord.keyWord}")
            .setView(customView)
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