package com.completewordproblems.fourthgrade.fragments.strategies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.completewordproblems.fourthgrade.R
import com.completewordproblems.fourthgrade.Wizard

/**
 * A simple [Fragment] subclass.
 * Use the [SolveFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SolveFragment : StrategyFragmentBase("Solve the problem") {

    lateinit var answerText: EditText
    lateinit var submitButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_solve, container, false)
        setupWordProblemText(view)
        setupNavigation(view)
        answerText = view.findViewById(R.id.answer_text)
        submitButton = view.findViewById(R.id.submit_button)
        submitButton.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            if (Wizard.currentProblem.answer == answerText.text.toString()) {
                builder.setMessage("That's right!")
            } else {
                builder.setMessage("${answerText.text} isn't the correct answer. Please try again.")
            }
            builder.setPositiveButton(android.R.string.ok, null)
            builder.create().show()
        }
        return view
    }
}