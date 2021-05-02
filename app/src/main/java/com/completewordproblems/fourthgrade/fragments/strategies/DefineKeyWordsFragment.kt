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

private const val LOG_TAG = "DefineKeyWordsFragment"

/**
 * A simple [Fragment] subclass.
 * Use the [DefineKeyWordsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DefineKeyWordsFragment : StrategyFragmentBase("Inspect key words"),
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
    private var standards: ArrayList<Standard> = ArrayList()
    private var incompleteStandards = 0
    private val completedKeyWords: ArrayList<KeyWord> = ArrayList()

    private var progressTotal: Int = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_define_key_words, container, false)
        setupWordProblemText(view)
        setupNavigation(view)
        incompleteKeyWords = Wizard.currentProblem.getKeyWords() as ArrayList<KeyWord>
        updateTextView()
        Wizard.currentProblem.concepts.forEach { concept ->
            concept.standards.forEach { standard ->
                standards.add(standard)
            }
        }
        if (standards.isNotEmpty()) {
            val recyclerView = view.findViewById<RecyclerView>(R.id.concepts_recycler_view)
            recyclerView.layoutManager = LinearLayoutManager(activity)
            recyclerView.adapter = RecyclerStandardsAdapter(standards, this)
        }
        incompleteStandards = standards.size
        progressTotal = (incompleteKeyWords.size + incompleteStandards) * 100
        view.findViewById<TextView>(R.id.denominator).text =
            (incompleteKeyWords.size + incompleteStandards).toString()
        numeratorTextView = view.findViewById(R.id.numerator)
        progressBar = view.findViewById(R.id.progress_bar)
        progressBar.max = progressTotal

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
            incompleteKeyWords.remove(keyWord)
            updateTextView()
            updateProgressBar()
        }
    }

    private fun updateProgressBar() {
        progress = min(progress + 100, progressTotal)
        numeratorTextView.text =
            ((progressTotal / 100) - (incompleteKeyWords.size + incompleteStandards)).toString()
    }

    override fun onStandardClicked(standard: Standard) {
        Log.d("JEDI", "onStandardClicked ${standard.id}")
        incompleteStandards --
        updateProgressBar()
    }
}