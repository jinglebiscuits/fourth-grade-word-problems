package com.completewordproblems.fourthgrade.customviews

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageButton
import android.widget.RadioGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import com.completewordproblems.fourthgrade.R

class DrawLayout (
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    init {
        inflate(context, R.layout.draw_layout, this)

        val undoButton: ImageButton = findViewById(R.id.undo_btn)
        val canvasView: CanvasView = findViewById(R.id.canvas_view)
        undoButton.setOnClickListener {
            canvasView.undo()
        }
        val toolSelection: RadioGroup = findViewById(R.id.tool_selection)
        toolSelection.check(R.id.draw_tool)
        toolSelection.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.draw_tool) {
                canvasView.switchToDraw()
            } else {
                canvasView.switchToErase()
            }
        }
    }
}