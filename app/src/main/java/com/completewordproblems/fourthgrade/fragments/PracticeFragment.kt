package com.completewordproblems.fourthgrade.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.completewordproblems.fourthgrade.R
import com.completewordproblems.fourthgrade.Wizard
import com.completewordproblems.fourthgrade.models.Strategy

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PracticeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PracticeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_practice, container, false)
        view.findViewById<Button>(R.id.start_button).setOnClickListener {
            // TODO: 4/4/21 This navigation should be based on the Student's strategy algorithm
            val fragTransitionId: Int = when (Wizard.currentStudent.strategies[Wizard.currentStrategyIndex]) {
                Strategy.READ_THE_PROBLEM -> R.id.action_practiceFragment_to_readTheProblemFragment
                Strategy.INSPECT_KEY_WORDS -> R.id.action_practiceFragment_to_defineKeyWordsFragment
                Strategy.WHAT_ARE_YOU_LOOKING_FOR -> R.id.action_practiceFragment_to_whatAreYouLookingForFragment
                Strategy.WHAT_INFORMATION_IS_NEEDED -> R.id.action_practiceFragment_to_whatIsGivenFragment
                Strategy.DRAW_THE_SCENE -> R.id.action_practiceFragment_to_drawSceneFragment
                Strategy.WRITE_THE_EQUATION -> R.id.action_practiceFragment_to_createExpressionFragment
                Strategy.SOLVE_THE_PROBLEM -> R.id.action_practiceFragment_to_solveFragment
            }
            view.findNavController()
                .navigate(fragTransitionId)
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
         * @return A new instance of fragment PracticeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PracticeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}