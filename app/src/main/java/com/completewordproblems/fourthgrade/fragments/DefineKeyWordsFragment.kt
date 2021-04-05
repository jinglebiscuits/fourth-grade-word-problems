package com.completewordproblems.fourthgrade.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.completewordproblems.fourthgrade.R
import com.google.android.material.progressindicator.LinearProgressIndicator
import kotlin.math.max
import kotlin.math.min

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DefineKeyWordsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DefineKeyWordsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var progress: Int = 0
    set(value) {
        progressBar.progress = value
        field = value
    }
    private lateinit var progressBar: LinearProgressIndicator

    // TODO: 4/4/21 this needs to be set by the word problem
    private var progressTotal: Int = 5

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
        val view: View = inflater.inflate(R.layout.fragment_define_key_words, container, false)
        view.findViewById<Button>(R.id.test_define_button).setOnClickListener {
            progress = min(progress + 1, progressTotal)
        }
        view.findViewById<Button>(R.id.test_undefine_button).setOnClickListener {
            progress = max(progress - 1, 0)
        }
        progressBar = view.findViewById<LinearProgressIndicator>(R.id.progress_bar)
        progressBar.max = 5
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DefineKeyWordsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DefineKeyWordsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}