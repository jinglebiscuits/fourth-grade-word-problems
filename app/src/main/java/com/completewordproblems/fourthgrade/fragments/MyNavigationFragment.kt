package com.completewordproblems.fourthgrade.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.completewordproblems.fourthgrade.R
import com.completewordproblems.fourthgrade.Wizard

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyNavigationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyNavigationFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        if (Wizard.currentStudent == null) {
            findNavController().navigate(R.id.action_myNavigationFragment_to_loginFragment)
        }
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyNavigationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyNavigationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}