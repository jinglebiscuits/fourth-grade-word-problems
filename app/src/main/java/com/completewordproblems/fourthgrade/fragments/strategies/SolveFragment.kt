package com.completewordproblems.fourthgrade.fragments.strategies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.completewordproblems.fourthgrade.R

/**
 * A simple [Fragment] subclass.
 * Use the [SolveFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SolveFragment : StrategyFragmentBase("Solve the problem") {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_solve, container, false)
        setupWordProblemText(view)
        return view
    }
}