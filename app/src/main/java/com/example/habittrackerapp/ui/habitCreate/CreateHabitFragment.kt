package com.example.habittrackerapp.ui.habitCreate

import com.example.habittrackerapp.data.ColorType
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.habittrackerapp.R
import com.example.habittrackerapp.data.Habit
import com.example.habittrackerapp.data.HabitPriority
import com.example.habittrackerapp.data.HabitType
import com.example.habittrackerapp.databinding.FragmentCreateHabitBinding
import com.example.habittrackerapp.vm.CreateHabitViewModel
import java.util.UUID

class CreateHabitFragment : Fragment() {

    private lateinit var binding: FragmentCreateHabitBinding
    private lateinit var habitCreateViewModel: CreateHabitViewModel

    private var habitType: String? = null
    private var habitColor: ColorType? = null
    private lateinit var colors: Map<TextView, ColorType>
    private var habitToEdit: Habit? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateHabitBinding.inflate(inflater)
        habitCreateViewModel =
            ViewModelProviders.of(requireActivity()).get(CreateHabitViewModel::class.java)
        setListeners()
        setSpinnerAdapter()
        arguments?.let {
            val habitId = it.getSerializable(ARG_HABIT_ID)
            habitToEdit = habitId.let { id -> habitCreateViewModel.findHabitById(id as UUID) }
            habitToEdit?.let { loadFormFields(habitToEdit!!) }
        }
        setRadioButton()
        return binding.root
    }

    private val onSaveHabitClick = View.OnClickListener {
        if (isValid()) {
            addHabit()
            goBack()
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.toast_fill_fields),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun goBack() {
        findNavController().popBackStack()
    }

    private fun loadFormFields(habit: Habit) {
        binding.nameTextField.setText(habit.name)
        binding.descriptionTextField.setText(habit.description)
        binding.countField.setText(habit.executionCount.toString())
        binding.periodField.setText(habit.periodicity.toString())
        habitType = habit.type
        val spinnerPos: Int = habit.priority.value
        binding.prioritySpinner.setSelection(spinnerPos)
        setColor(ColorType.values().first { colorType -> colorType.colorCode == habit.color })
    }

    private fun addHabit() {
        val name = binding.nameTextField.text.toString()
        val description = binding.descriptionTextField.text.toString()
        val priority = binding.prioritySpinner.selectedItem.toString()
        val count = binding.countField.text.toString().toInt()
        val period = binding.periodField.text.toString().toInt()
        if (habitToEdit != null)
            habitCreateViewModel.editHabit(
                habitToEdit!!, name,
                description,
                priority,
                habitType!!,
                count,
                period,
                habitColor!!
            )
        else habitCreateViewModel.addHabit(
            name,
            description,
            priority,
            habitType!!,
            count,
            period,
            habitColor!!
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
            requireContext(),
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
            (binding.habitType[i] as RadioButton).isChecked = true
        }
    }

    companion object {
        const val ARG_HABIT_ID = "Habit"

        fun newInstance(habitId: UUID) =
            CreateHabitFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_HABIT_ID, habitId)
                }
            }
    }
}