package cn.itjesse.subwaydic;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateChangedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.umeng.analytics.MobclickAgent;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment implements OnDateChangedListener, OnMonthChangedListener {

    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    private CharSequence mTitle;
    private int group = 1;

    public CalendarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CalendarFragment.
     */
    public static CalendarFragment newInstance() {
        CalendarFragment fragment = new CalendarFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mTitle = getString(R.string.group_1);
        changeSubtitle();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
        MaterialCalendarView calendarView = (MaterialCalendarView) rootView.findViewById(R.id.calendarView);
        calendarView.setFirstDayOfWeek(Calendar.MONDAY);
//        try {
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//            Date minDate = formatter.parse("2015-01-01");
//            calendarView.setMinimumDate(minDate.getTime());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        calendarView.setOnDateChangedListener(this);
        calendarView.setOnMonthChangedListener(this);
        return rootView;
    }

    @Override
    public void onDateChanged(MaterialCalendarView widget, CalendarDay date) {
        if(date == null) {

        }
        else {
            Toast.makeText(getActivity().getBaseContext(), FORMATTER.format(date.getDate()), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        Toast.makeText(getActivity().getBaseContext(), FORMATTER.format(date.getDate()), Toast.LENGTH_SHORT).show();
    }

    private void changeGroup() {
        switch (group){
            case 1:
                mTitle = getString(R.string.group_1);
                break;
            case 2:
                mTitle = getString(R.string.group_2);
                break;
            case 3:
                mTitle = getString(R.string.group_3);
                break;
            case 4:
                mTitle = getString(R.string.group_4);
                break;
            default:
                mTitle = null;
        }
        changeSubtitle();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.calendar, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_switch_group) {
            Toast.makeText(getActivity(), "menu text is " + item.getTitle(), Toast.LENGTH_SHORT).show();
            group ++;
            if(group > 4)
                group = 1;
            changeGroup();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeSubtitle() {
        ActionBar actionBar = ((ActionBarActivity)getActivity()).getSupportActionBar();
        actionBar.setSubtitle(mTitle);
    }

    private void resetSubtitle() {
        ActionBar actionBar = ((ActionBarActivity)getActivity()).getSupportActionBar();
        actionBar.setSubtitle(null);
    }

    public void onResume() {
        super.onResume();
        Log.d("DEBUG========", "onResume");
        changeSubtitle();
        MobclickAgent.onPageStart("MainScreen"); //统计页面
    }

    public void onPause() {
        super.onPause();
        Log.d("DEBUG========", "onPause");
        resetSubtitle();
        MobclickAgent.onPageEnd("MainScreen");
    }

}
