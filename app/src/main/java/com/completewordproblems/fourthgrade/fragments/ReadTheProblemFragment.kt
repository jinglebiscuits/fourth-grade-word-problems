package com.completewordproblems.fourthgrade.fragments

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.completewordproblems.fourthgrade.R
import com.completewordproblems.fourthgrade.Wizard
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val MY_DATA_CHECK_CODE: Int = 100
private const val LOG_TAG = "ReadTheProblemFragment"

/**
 * A simple [Fragment] subclass.
 * Use the [ReadTheProblemFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReadTheProblemFragment : Fragment(), TextToSpeech.OnInitListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var wordProblemText: String
    private lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        val intent: Intent = Intent()
        intent.action = TextToSpeech.Engine.ACTION_CHECK_TTS_DATA
        startActivityForResult(intent, MY_DATA_CHECK_CODE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_read_the_problem, container, false)
        val text: TextView = view.findViewById(R.id.word_problem_text)
        text.text = Wizard.getWordProblem().getWordProblemText()
        wordProblemText = text.text as String
        val readAloudButton: View = view.findViewById(R.id.read_aloud_button)
        readAloudButton.setOnClickListener(View.OnClickListener {
            // TODO: 4/4/21 this tts might not be ready
            tts.speak(wordProblemText, TextToSpeech.QUEUE_FLUSH, null, "word_problem")
        })
        view.findViewById<View>(R.id.next_button).setOnClickListener(View.OnClickListener {
            // TODO: 4/4/21 This navigation should be based on the Student's strategy algorithm
            view.findNavController()
                .navigate(R.id.action_practiceFragment_to_defineKeyWordsFragment)
        })
        view.findViewById<View>(R.id.back_button).setOnClickListener {
            view.findNavController().navigate(R.id.practiceFragment)
        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(LOG_TAG, "result I'm looking for: " + TextToSpeech.Engine.CHECK_VOICE_DATA_PASS)
        Log.d(
            LOG_TAG,
            String.format("requestCode: $requestCode, result: $resultCode", requestCode, resultCode)
        )
        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                tts = TextToSpeech(context, this)
            }
        }
    }

    override fun onInit(p0: Int) {
        Log.d(LOG_TAG, "onInit $p0")
        tts.language = Locale.US
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ReadTheProblemFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ReadTheProblemFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}