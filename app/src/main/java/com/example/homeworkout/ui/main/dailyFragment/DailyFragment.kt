package com.example.homeworkout.ui.main.dailyFragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.homeworkout.R
import com.example.homeworkout.data.local.room.model.DoneWorkout
import com.example.homeworkout.data.local.share.sharePreferenceUtils
import com.example.homeworkout.databinding.FragmentDailyBinding
import com.example.homeworkout.ui.base.BaseFragment
import com.example.homeworkout.util.CalenderUtils
import com.example.homeworkout.util.Utils
import java.time.LocalDate
import javax.inject.Inject


class DailyFragment : BaseFragment<FragmentDailyBinding,DailyViewModel>() {
    override fun layoutRes(): Int = R.layout.fragment_daily

    override fun viewModelClass(): Class<DailyViewModel> = DailyViewModel::class.java

    override fun firebaseClass() {

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initValue() {
        doneWorkoutDoneHistoryAdapter = WorkoutDoneHistoryAdapter()
        binding.dailyWorkoutList.adapter = doneWorkoutDoneHistoryAdapter
        CalenderUtils.selectedDate = LocalDate.now()
        days = ArrayList()
        calenderAdapter = CalenderAdapter(itemCalenderOnClick)
        binding.calendarRecyclerView.adapter = calenderAdapter

        getTheDailyWorkout(CalenderUtils.formattedDate(LocalDate.now())!!)
        addEvents()
        setWeekView()
    }
    @Inject
    lateinit var sharePreferenceUtils : sharePreferenceUtils
    lateinit var doneWorkoutDoneHistoryAdapter: WorkoutDoneHistoryAdapter
    lateinit var calenderAdapter: CalenderAdapter
    var days: ArrayList<LocalDate?>? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDailyBinding.inflate(layoutInflater)
        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun addEvents() {
        binding.nextBtn.setOnClickListener {
            CalenderUtils.selectedDate = CalenderUtils.selectedDate!!.plusWeeks(1)
            setWeekView()
        }
        binding.preBtn.setOnClickListener {
            CalenderUtils.selectedDate = CalenderUtils.selectedDate!!.minusWeeks(1)
            setWeekView()
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setWeekView() {
        binding.monthTitle.text = CalenderUtils.monthYearFromDate(CalenderUtils.selectedDate!!)
        val days: ArrayList<LocalDate?>? = CalenderUtils.daysInWeekArray(CalenderUtils.selectedDate!!)
        calenderAdapter.updateList(days!!)
    }

    private fun updateTotal(data : List<DoneWorkout>) {
        binding.recordValue.text = data.size.toString()
        var total : Int = 0
        var timeTotal : Long = 0
        for(item in data){
            total += item.calorie!!.toInt()
            timeTotal += item.time!!.toLong()
        }
        binding.totalCaloValue.text = total.toString()
        binding.timeValue.text = Utils.createTime(timeTotal)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    val itemCalenderOnClick  : (Int, LocalDate) -> Unit = { position, date ->
        CalenderUtils.selectedDate = date;
        setWeekView()
        getTheDailyWorkout(CalenderUtils.formattedDate(date)!!)

    }

    private fun getTheDailyWorkout(date : String) {
        val userId = sharePreferenceUtils.getUser(requireContext()).id
        viewModel.getDailyWorkout(userId,date)
        viewModel.listDoneWorkout.observe(viewLifecycleOwner) {
            doneWorkoutDoneHistoryAdapter.differ.submitList(it)
            updateTotal(it)
        }
    }


}