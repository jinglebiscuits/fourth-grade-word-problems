package com.completewordproblems.fourthgrade.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import com.completewordproblems.fourthgrade.R
import com.completewordproblems.fourthgrade.Wizard

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RemoveInformationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RemoveInformationFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var wordProblemTextView: TextView

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
        val view: View = inflater.inflate(R.layout.fragment_draw_scene, container, false)
        wordProblemTextView = view.findViewById(R.id.word_problem_text)
        wordProblemTextView.text = Wizard.currentProblem.getWordProblemText()
        view.findViewById<View>(R.id.next_button).setOnClickListener(View.OnClickListener {
            // TODO: 4/4/21 This navigation should be based on the Student's strategy algorithm
            view.findNavController()
                .navigate(R.id.action_practiceFragment_to_drawSceneFragment)
        })
        view.findViewById<View>(R.id.back_button).setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_practiceFragment_to_whatIsGivenFragment)
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
         * @return A new instance of fragment RemoveInformationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RemoveInformationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}