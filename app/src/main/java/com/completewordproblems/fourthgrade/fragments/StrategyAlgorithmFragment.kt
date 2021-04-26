package com.completewordproblems.fourthgrade.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.completewordproblems.fourthgrade.R
import com.completewordproblems.fourthgrade.strategy.OnDragStartListener
import com.completewordproblems.fourthgrade.strategy.RecyclerListAdapter
import com.completewordproblems.fourthgrade.strategy.SimpleItemTouchHelperCallback

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StrategyAlgorithmFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StrategyAlgorithmFragment : Fragment(), OnDragStartListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var itemTouchHelper: ItemTouchHelper

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
        val view: View = inflater.inflate(R.layout.fragment_strategy_algorithm, container, false)
        val adapter = RecyclerListAdapter(this)
        val recyclerList: RecyclerView = view.findViewById(R.id.strategy_list)
        recyclerList.setHasFixedSize(true)
        recyclerList.layoutManager = LinearLayoutManager(activity)
        recyclerList.adapter = adapter
        val callback: ItemTouchHelper.Callback = SimpleItemTouchHelperCallback(adapter)
        itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerList)
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StrategyAlgorithmFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StrategyAlgorithmFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onDragStarted(viewHolder: RecyclerView.ViewHolder) {
        Log.d("TAG", "onDragStarted")
    }
}