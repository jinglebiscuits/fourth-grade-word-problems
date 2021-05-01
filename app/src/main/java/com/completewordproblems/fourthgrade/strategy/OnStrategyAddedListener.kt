package com.completewordproblems.fourthgrade.strategy

import com.completewordproblems.fourthgrade.models.Strategy

interface OnStrategyAddedListener {
    fun onStrategyAdded(strategy: Strategy)
}