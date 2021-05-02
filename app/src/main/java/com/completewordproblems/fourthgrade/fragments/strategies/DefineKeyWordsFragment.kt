package com.completewordproblems.fourthgrade.fragments.strategies

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
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.completewordproblems.fourthgrade.R
import com.completewordproblems.fourthgrade.Wizard
import com.completewordproblems.fourthgrade.models.KeyWord
import com.completewordproblems.fourthgrade.models.Standard
import com.completewordproblems.fourthgrade.strategy.OnStandardClickListener
import com.completewordproblems.fourthgrade.strategy.RecyclerStandardsAdapter
import com.google.android.material.progressindicator.LinearProgressIndicator
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
class DefineKeyWordsFragment : StrategyFragmentBase("Define key words"),
    VocabularyDialogFragment.VocabularyDialogListener, OnStandardClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var progress: Int = 0
        set(value) {
            Log.d(LOG_TAG, "progress: $progress")
            val currentPosition: Int = progress
            val animator = ObjectAnimator.ofInt(progressBar, "progress", currentPosition, value)
                .setDuration(175)
            animator.startDelay = 250
            animator.start()
            field = value
        }
    private lateinit var progressBar: LinearProgressIndicator
    private lateinit var numeratorTextView: TextView
    private var incompleteKeyWords: ArrayList<KeyWord> = ArrayList()
    private var incompleteStandards: ArrayList<Standard> = ArrayList()
    private val completedKeyWords: ArrayList<KeyWord> = ArrayList()

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
        incompleteKeyWords = Wizard.currentProblem.getKeyWords() as ArrayList<KeyWord>
        setupWordProblemText(view)
        setupNavigation(view)
        updateTextView()
        progressTotal = incompleteKeyWords.size * 100
        view.findViewById<TextView>(R.id.denominator).text = incompleteKeyWords.size.toString()
        numeratorTextView = view.findViewById(R.id.numerator)
        progressBar = view.findViewById(R.id.progress_bar)
        progressBar.max = progressTotal

        Wizard.currentProblem.concepts.forEach { concept ->
            concept.standards.forEach { standard ->
                incompleteStandards.add(standard)
            }
        }
        if (incompleteStandards.isNotEmpty()) {
            val recyclerView = view.findViewById<RecyclerView>(R.id.concepts_recycler_view)
            recyclerView.layoutManager = LinearLayoutManager(activity)
            recyclerView.adapter = RecyclerStandardsAdapter(incompleteStandards, this)
        }

        return view
    }

    private fun updateTextView() {
        val ss =
            SpannableString(Wizard.currentProblem.getWordProblemText())
        incompleteKeyWords.forEach {
            ss.setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    val vocabDialog = VocabularyDialogFragment(this@DefineKeyWordsFragment, it)
                    vocabDialog.show(childFragmentManager, VocabularyDialogFragment.TAG)
                }
            }, it.start, it.end, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
        }

        wordProblemTextView.text = ss
        wordProblemTextView.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun onSubmit(keyWord: KeyWord, isCorrect: Boolean) {
        if (isCorrect) {
            progress = min(progress + 100, progressTotal)
            incompleteKeyWords.remove(keyWord)
            updateTextView()
            numeratorTextView.text = ((progressTotal / 100) - incompleteKeyWords.size).toString()
        }
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

    override fun onStandardClicked(standard: Standard) {
        Log.d("JEDI", "onStandardClicked ${standard.id}")
    }
}