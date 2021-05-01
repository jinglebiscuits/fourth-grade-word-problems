package com.completewordproblems.fourthgrade.fragments.strategies

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.completewordproblems.fourthgrade.MainActivity

open class StrategyFragmentBase(private val title: String) : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).supportActionBar?.title = title
    }
}