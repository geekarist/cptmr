package me.cpele.cptmr.ui.main

import android.app.TimePickerDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.cpele.cptmr.R

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, bundle: Bundle?) {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val timerView: CircularTimerView = view.findViewById(R.id.circular_timer)

        timerView.setOnClickListener {
            val listener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                timerView.setTime(hourOfDay, minute)
                viewModel.startTimer()
            }
            TimePickerDialog(context, listener, 0, 0, true).show()
        }

        viewModel.timeLive.observe(viewLifecycleOwner) { time ->
            timerView.setTime(time.hour, time.minute)
        }
    }
}