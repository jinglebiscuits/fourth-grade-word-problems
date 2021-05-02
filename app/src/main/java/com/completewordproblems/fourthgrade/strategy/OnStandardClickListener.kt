package com.completewordproblems.fourthgrade.strategy

import com.completewordproblems.fourthgrade.models.Standard

interface OnStandardClickListener {
    fun onStandardClicked(standard: Standard)
}