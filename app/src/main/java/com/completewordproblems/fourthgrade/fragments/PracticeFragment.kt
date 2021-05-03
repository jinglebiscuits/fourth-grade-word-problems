package com.completewordproblems.fourthgrade.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.completewordproblems.fourthgrade.MainActivity
import com.completewordproblems.fourthgrade.R
import com.completewordproblems.fourthgrade.Wizard

class PracticeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.app_name)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_practice, container, false)
        view.findViewById<Button>(R.id.start_button).setOnClickListener {
            view.findNavController()
                .navigate(Wizard.getTransitionId())
        }
        return view
    }
}