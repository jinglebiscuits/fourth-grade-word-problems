package com.completewordproblems.fourthgrade.fragments.strategies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.completewordproblems.fourthgrade.R

class CreateExpressionFragment : StrategyFragmentBase("Write the equation") {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View =
            inflater.inflate(R.layout.fragment_create_expression, container, false)
        setupWordProblemText(view)
        setupNavigation(view)
        return view
    }
}