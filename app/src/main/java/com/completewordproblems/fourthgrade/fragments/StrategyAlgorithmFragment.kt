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
import com.completewordproblems.fourthgrade.MainActivity
import com.completewordproblems.fourthgrade.R
import com.completewordproblems.fourthgrade.Wizard
import com.completewordproblems.fourthgrade.models.Strategy
import com.completewordproblems.fourthgrade.strategy.*

class StrategyAlgorithmFragment : Fragment(), OnDragStartListener, OnStrategyAddedListener {

    lateinit var itemTouchHelper: ItemTouchHelper
    lateinit var unusedStrategiesListView: RecyclerView
    lateinit var unusedStrategiesListAdapter: UnusedStrategiesListAdapter
    lateinit var usedStrategiesListAdapter: RecyclerListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.app_name)
    }

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

            Wizard.currentStudent?.strategies = usedStrategiesListAdapter.strategiesToUse
        }
        return view
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