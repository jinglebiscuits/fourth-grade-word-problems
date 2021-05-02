package com.completewordproblems.fourthgrade.fragments.strategies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.completewordproblems.fourthgrade.R

class WhatInformationIsNeeded : StrategyFragmentBase("What information is needed?") {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View =
            inflater.inflate(R.layout.fragment_what_information_is_needed, container, false)
        setupWordProblemText(view)
        setupNavigation(view)

        return view
    }
}