package com.completewordproblems.fourthgrade.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.completewordproblems.fourthgrade.MainActivity
import com.completewordproblems.fourthgrade.R
import com.completewordproblems.fourthgrade.Wizard

class MyNavigationFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Wizard.currentStudent == null) {
            findNavController().navigate(R.id.action_myNavigationFragment_to_loginFragment)
        }
        (activity as MainActivity).supportActionBar?.title = getString(R.string.app_name)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_my_navigation, container, false)
        view.findViewById<Button>(R.id.practice_button).setOnClickListener {
            context?.let { it1 -> Wizard.setCurrentProblem(it1) }
            view.findNavController().navigate(R.id.action_myNavigationFragment_to_practiceFragment)
        }
        view.findViewById<Button>(R.id.growth_button).setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_myNavigationFragment_to_yourGrowthFragment)
        }
        view.findViewById<Button>(R.id.strategy_button).setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_myNavigationFragment_to_strategyAlgorithmFragment)
        }
        view.findViewById<Button>(R.id.sign_out_button).setOnClickListener {
            view.findNavController().navigate(R.id.action_myNavigationFragment_to_loginFragment)
        }
        return view
    }
}