package com.completewordproblems.fourthgrade.fragments.strategies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.completewordproblems.fourthgrade.R

class WhatAreYouLookingForFragment : StrategyFragmentBase("What are you looking for?") {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View =
            inflater.inflate(R.layout.fragment_what_are_you_looking_for, container, false)
        setupWordProblemText(view)
        setupNavigation(view)
        return view
    }
}