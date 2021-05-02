package com.completewordproblems.fourthgrade.fragments.strategies

import android.app.Dialog
import android.os.Bundle
import android.util.Log
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
) : DialogFragment() {

    private var correctPosition = -1
    private var chosenPosition = -1
    private lateinit var dialog: AlertDialog

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val customView = layoutInflater.inflate(R.layout.dialog_vocabulary_quiz, null)
        val definitionOptions: ListView =
            customView.findViewById(R.id.definition_options)
        val options: MutableList<String> = arrayListOf()
        FourthGradeDictionary.getWord(keyWord.keyWord)
            ?.let { options.addAll(it.incorrectDefinitions) }
        FourthGradeDictionary.getWord(keyWord.keyWord)?.let { options.add(it.correctDefinition) }
        options.shuffle()
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(), android.R.layout.simple_list_item_single_choice,
            options
        )
        correctPosition =
            options.indexOf(FourthGradeDictionary.getWord(keyWord.keyWord)?.correctDefinition)
        definitionOptions.adapter = adapter
        definitionOptions.setOnItemClickListener { parent, view, position, id ->
            Log.d("VocabDialogFrag", "position is $position")
            chosenPosition = position
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = true
        }
        dialog = AlertDialog.Builder(requireContext())
            .setTitle("Select the correct definition for: ${keyWord.keyWord}")
            .setView(customView)
            .setPositiveButton(
                "Submit"
            ) { _, _ ->
                vocabularyDialogListener.onSubmit(
                    keyWord,
                    correctPosition == chosenPosition
                )
            }
            .create()
        dialog.setOnShowListener { dialog ->
            if (correctPosition != -1) {
                (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
            }
        }
        return dialog
    }

    interface VocabularyDialogListener {
        fun onSubmit(keyWord: KeyWord, isCorrect: Boolean)
    }

    companion object {
        const val TAG = "VOCABULARY_DIALOG_FRAGMENT"
    }
}