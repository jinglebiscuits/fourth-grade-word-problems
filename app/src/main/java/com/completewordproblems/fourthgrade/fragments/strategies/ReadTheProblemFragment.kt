package com.completewordproblems.fourthgrade.fragments.strategies

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.completewordproblems.fourthgrade.R
import java.util.*

private const val MY_DATA_CHECK_CODE: Int = 100
private const val LOG_TAG = "ReadTheProblemFragment"

class ReadTheProblemFragment : StrategyFragmentBase("Read the problem"),
    TextToSpeech.OnInitListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var wordProblemText: String
    private lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent: Intent = Intent()
        intent.action = TextToSpeech.Engine.ACTION_CHECK_TTS_DATA
        startActivityForResult(intent, MY_DATA_CHECK_CODE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_read_the_problem, container, false)
        setupWordProblemText(view)
        setupNavigation(view)
        wordProblemText = wordProblemTextView.text as String
        val readAloudButton: View = view.findViewById(R.id.read_aloud_button)
        readAloudButton.setOnClickListener(View.OnClickListener {
            // TODO: 4/4/21 this tts might not be ready
            tts.speak(wordProblemText, TextToSpeech.QUEUE_FLUSH, null, "word_problem")
        })

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
}