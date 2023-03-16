package com.example.habittrackerapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.habittrackerapp.HabitTracker
import com.example.habittrackerapp.HabitTrackerController
import com.example.habittrackerapp.data.ColorType
import com.example.habittrackerapp.data.Habit
import com.example.habittrackerapp.data.HabitPriority
import com.example.habittrackerapp.data.HabitType
import com.example.habittrackerapp.databinding.ActivityCreateHabitBinding


class CreateHabitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateHabitBinding
    private lateinit var controller: HabitTrackerController


    private var habitType: String? = null
    private var habitColor: ColorType? = null
    private lateinit var colors: Map<TextView, ColorType>

    private val onSaveHabitClick = View.OnClickListener {
        if (isValid()) {
            addHabit()
            goToHabitList()
        } else {
            Toast.makeText(this, "fill all fields", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateHabitBinding.inflate(layoutInflater)
        controller = HabitTracker.applicationContext().controller
        setContentView(binding.root)
        setListeners()
        setSpinnerAdapter()
        val arg = intent.extras
        if (arg != null) {
            loadFormFields(arg.get("habit") as Habit)
        }
        setRadioButton()
    }

    private fun loadFormFields(habit: Habit) {
        binding.nameTextField.setText(habit.name)
        binding.descriptionTextField.setText(habit.description)
        binding.countField.setText(habit.executionCount.toString())
        binding.periodField.setText(habit.periodicity.toString())
        habitType = habit.type
        val spinnerPos: Int =
            HabitPriority.values().map { it.toString().lowercase() }.indexOf(habit.priority)
        binding.prioritySpinner.setSelection(spinnerPos)
        setColor(habit.color)
    }

    private fun addHabit() {
        val name = binding.nameTextField.text.toString()
        val description = binding.descriptionTextField.text.toString()
        val priority = binding.prioritySpinner.selectedItem.toString()
        val count = binding.countField.text.toString().toInt()
        val period = binding.periodField.text.toString().toInt()
        controller.addHabit(
            Habit(
                name,
                description,
                priority,
                habitType!!,
                count,
                period,
                habitColor!!
            )
        )
    }

    private fun isValid(): Boolean {
        return !(binding.nameTextField.text.isEmpty() ||
                binding.descriptionTextField.text.isEmpty() ||
                binding.countField.text.isEmpty() ||
                binding.periodField.text.isEmpty() ||
                binding.habitType.checkedRadioButtonId == -1 || habitColor == null
                )
    }


    private fun goToHabitList() {
        Intent(this, HabitListActivity::class.java)
            .run { startActivity(this) }
    }

    private fun setListeners() {
        binding.buttonSaveHabit.setOnClickListener(onSaveHabitClick)
        setColorsButtonListeners()
        binding.habitType.setOnCheckedChangeListener { _, checkedId ->
            binding.habitType.findViewById<RadioButton>(checkedId)?.apply {
                habitType = this.text.toString()
            }
        }
    }

    private fun setColorsButtonListeners() {
        colors = mapOf(
            binding.greenColor to ColorType.Green,
            binding.blueColor to ColorType.Blue,
            binding.pinkColor to ColorType.Pink,
            binding.yellowColor to ColorType.Yellow,
            binding.purpleColor to ColorType.Purple,
        )

        colors.forEach { (color, type) ->
            color.setOnClickListener {
                colors.keys.forEach { k -> k.setPadding(0, 0, 0, 0) }
                it.setPadding(0, 50, 0, 0)
                habitColor = type
            }
        }
    }

    private fun setSpinnerAdapter() {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            HabitPriority.values().map { it.toString().lowercase() }
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.prioritySpinner.adapter = adapter
        binding.prioritySpinner.setSelection(0)
    }

    private fun setColor(type: ColorType) {
        colors.forEach { (c, t) ->
            if (t == type) {
                c.setPadding(0, 50, 0, 0)
                habitColor = t
            }
        }
    }

    private fun setRadioButton() {
        if (habitType != null) {
            val i = HabitType.values().map { it.toString().lowercase() }.indexOf(habitType)
            binding.habitType.check(i)
        }
    }
}
