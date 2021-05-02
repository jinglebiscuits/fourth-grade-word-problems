package com.completewordproblems.fourthgrade.fragments.strategies

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.completewordproblems.fourthgrade.MainActivity
import com.completewordproblems.fourthgrade.R
import com.completewordproblems.fourthgrade.Wizard

open class StrategyFragmentBase(private val title: String) : Fragment() {

    lateinit var wordProblemTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).supportActionBar?.title = title
    }

    fun setupWordProblemText(parentView: View) {
        wordProblemTextView = parentView.findViewById(R.id.word_problem_text)
        wordProblemTextView.text = Wizard.currentProblem.getWordProblemText()
    }
}