package com.completewordproblems.fourthgrade.fragments.strategies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
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
 * Use the [DrawSceneFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DrawSceneFragment : StrategyFragmentBase("Draw the scene") {
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
        val view: View = inflater.inflate(R.layout.fragment_draw_scene, container, false)
        setupWordProblemText(view)

        view.findViewById<View>(R.id.next_button).setOnClickListener(View.OnClickListener {
            // TODO: 4/4/21 This navigation should be based on the Student's strategy algorithm
            view.findNavController()
                .navigate(R.id.action_practiceFragment_to_createExpressionFragment)
        })
        view.findViewById<View>(R.id.back_button).setOnClickListener {
            Wizard.currentStrategyIndex = Wizard.currentStrategyIndex + 1
            view.findNavController().navigate(Wizard.getTransitionId())
        }
        val saveButton: Button = view.findViewById(R.id.save_drawing)
        saveButton.setOnClickListener {
            saveBitmap()
            saveButton.isEnabled = false
        }
        return view
    }

    private fun saveBitmap() {
        Log.d("TAG", "save bitmap")
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DrawSceneFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DrawSceneFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}