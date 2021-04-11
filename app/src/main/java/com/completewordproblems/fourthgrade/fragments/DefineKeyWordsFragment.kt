package com.completewordproblems.fourthgrade.fragments

import android.animation.ObjectAnimator
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.completewordproblems.fourthgrade.R
import com.completewordproblems.fourthgrade.Wizard
import com.google.android.material.progressindicator.LinearProgressIndicator
import kotlin.math.max
import kotlin.math.min

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val LOG_TAG = "DefineKeyWordsFragment"

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
            Log.d(LOG_TAG, "progress: $progress")
            val currentPosition: Int = progress
            ObjectAnimator.ofInt(progressBar, "progress", currentPosition, value)
                .setDuration(100).start()
            field = value
        }
    private lateinit var progressBar: LinearProgressIndicator
    private lateinit var wordProblemTextView: TextView

    // TODO: 4/4/21 this needs to be set by the word problem
    private var progressTotal: Int = 100

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

        wordProblemTextView = view.findViewById(R.id.word_problem_text)
        val ss =
            SpannableString(Wizard.getWordProblem().getWordProblemText())
        Wizard.getWordProblem().getKeyWords().forEach {
            ss.setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(widget.context)
                    dialogBuilder.setTitle("WE know").setMessage("do you know?")
                    dialogBuilder.create().show()
                }
            }, it.start, it.end, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
        }

        wordProblemTextView.text = ss
        wordProblemTextView.movementMethod = LinkMovementMethod.getInstance()

        //region testing the progress bar
        view.findViewById<Button>(R.id.test_define_button).setOnClickListener {
            progress = min(progress + 10, progressTotal)
        }
        view.findViewById<Button>(R.id.test_undefine_button).setOnClickListener {
            progress = max(progress - 10, 0)
        }
        progressBar = view.findViewById<LinearProgressIndicator>(R.id.progress_bar)
        progressBar.max = progressTotal
        //endregion

        view.findViewById<View>(R.id.next_button).setOnClickListener(View.OnClickListener {
            // TODO: 4/4/21 This navigation should be based on the Student's strategy algorithm
            view.findNavController()
                .navigate(R.id.action_practiceFragment_to_whatAreYouLookingForFragment)
        })
        view.findViewById<View>(R.id.back_button).setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_practiceFragment_to_readTheProblemFragment)
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