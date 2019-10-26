package com.lyeng.notekeeper

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.color_selector.view.*

class ColourSelector @JvmOverloads
constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private var listOfColours = listOf(Color.BLUE, Color.RED, Color.GREEN)
    private var selectedColorIndex = 0

    init {
        val typedArray = context!!.obtainStyledAttributes(attrs, R.styleable.ColourSelector)
        listOfColours = typedArray.getTextArray(R.styleable.ColourSelector_colors)
            .map{
                Color.parseColor(it.toString())
            }
        typedArray?.recycle()

        orientation = LinearLayout.HORIZONTAL

        val inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflator.inflate(R.layout.color_selector, this)

        selectedColorr.setBackgroundColor(listOfColours[selectedColorIndex])

        colorSelectorArrowLeft.setOnClickListener {
            selectPreviousColor()
        }

        colorSelectorArrowRight.setOnClickListener {
            selectNextColor()
        }

        colorEnabled.setOnCheckedChangeListener { buttonView, isChecked -> broadcastColor() }
    }

    var selectedColorValue: Int = android.R.color.transparent
        set(value) {
            var index = listOfColours.indexOf(value)
            if (index == -1) {
                colorEnabled.isChecked = false
                index = 0
            } else {
                colorEnabled.isChecked = true
            }
            selectedColorIndex = index
            selectedColorr.setBackgroundColor(listOfColours[selectedColorIndex])
        }

    //    private var colorSelectedListener: ColorSelectListener? = null
//    private var colorSelectedListener: ((Int) -> Unit)? = null
    private var colorSelectedListeners: ArrayList<(Int) -> Unit> = arrayListOf()

    //Java style
//    interface ColorSelectListener {
//        fun onColorSelected(color: Int)
//    }

//    fun setColorSelectListener(listener: ColorSelectListener) {
//        this.colorSelectedListener = listener
//    }

    fun addListener(function: (Int) -> Unit) {
//        this.colorSelectedListener = color
        this.colorSelectedListeners.add(function)
    }

    // Java style
//    fun setSelectedColor(color: Int) {
//        var index = listOfColours.indexOf(color)
//        if (index == -1) {
//            colorEnabled.isChecked = false
//            index = 0
//        } else {
//            colorEnabled.isChecked = true
//        }
//        selectedColorIndex = index
//        selectedColorr.setBackgroundColor(listOfColours[selectedColorIndex])
//    }


    private fun selectPreviousColor() {
        if (selectedColorIndex == 0) {
            selectedColorIndex = listOfColours.lastIndex
        } else {
            selectedColorIndex--
        }
        selectedColorr.setBackgroundColor(listOfColours[selectedColorIndex])
        broadcastColor()
    }

    private fun selectNextColor() {
        if (selectedColorIndex == listOfColours.lastIndex) {
            selectedColorIndex = 0
        } else {
            selectedColorIndex++
        }
        selectedColorr.setBackgroundColor(listOfColours[selectedColorIndex])
        broadcastColor()
    }

    private fun broadcastColor() {
        val color = if (colorEnabled.isChecked)
            listOfColours[selectedColorIndex]
        else
            Color.TRANSPARENT
        this.colorSelectedListeners.forEach { function ->
            function(color)
        }
    }
}