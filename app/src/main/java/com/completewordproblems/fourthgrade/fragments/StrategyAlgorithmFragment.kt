package com.completewordproblems.fourthgrade.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.completewordproblems.fourthgrade.R
import com.completewordproblems.fourthgrade.Wizard
import com.completewordproblems.fourthgrade.models.Strategy
import com.completewordproblems.fourthgrade.strategy.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class StrategyAlgorithmFragment : Fragment(), OnDragStartListener, OnStrategyAddedListener {

    lateinit var itemTouchHelper: ItemTouchHelper
    lateinit var unusedStrategiesListView: RecyclerView
    lateinit var unusedStrategiesListAdapter: UnusedStrategiesListAdapter
    lateinit var usedStrategiesListAdapter: RecyclerListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_strategy_algorithm, container, false)
        usedStrategiesListAdapter = RecyclerListAdapter(this)
        val usedStrategiesRecyclerView: RecyclerView = view.findViewById(R.id.strategy_list)
        usedStrategiesRecyclerView.setHasFixedSize(true)
        usedStrategiesRecyclerView.layoutManager = LinearLayoutManager(activity)
        usedStrategiesRecyclerView.adapter = usedStrategiesListAdapter
        val callback: ItemTouchHelper.Callback =
            SimpleItemTouchHelperCallback(usedStrategiesListAdapter)
        itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(usedStrategiesRecyclerView)
        unusedStrategiesListView = view.findViewById(R.id.unused_strategies_list)
        unusedStrategiesListAdapter = UnusedStrategiesListAdapter(this)
        unusedStrategiesListView.setHasFixedSize(true)
        unusedStrategiesListView.layoutManager = LinearLayoutManager(activity)
        unusedStrategiesListView.adapter = unusedStrategiesListAdapter

        view.findViewById<Button>(R.id.save_strategy).setOnClickListener {
            //SW temporary hack. remove
            Wizard.onLogin("Scott")

            Wizard.currentStudent?.strategies = usedStrategiesListAdapter.stragiesToUse
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

    override fun onItemDismissed(strategy: Strategy) {
        Log.d("JEDI", "strategy dismissed $strategy")
        unusedStrategiesListAdapter.addStrategy(strategy)
    }

    override fun onStrategyAdded(strategy: Strategy) {
        usedStrategiesListAdapter.addStrategy(strategy)
    }


}