package com.example.mia.loop;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by mia on 3/26/15.
 */
public class TimePickerFragment extends DialogFragment  {
    public static final String EXTRA_TIME = "com.example.mia.loop.date";
    private static final String TAG = "TimePickerFragment";
    private Date mTime;

    private TimePicker.OnTimeChangedListener mTimeChangedListener = new TimePicker.OnTimeChangedListener() {
        @Override
        public void onTimeChanged(TimePicker view, int hour, int minute) {
            // grab a calendar instance
            Calendar calendar = Calendar.getInstance();
            // set calendar's time to the new hour & minute values
            calendar.set(Calendar.HOUR_OF_DAY, view.getCurrentHour());
            calendar.set(Calendar.MINUTE, view.getCurrentMinute());
            // convert Calendar to Date
            mTime = calendar.getTime();
            Log.d(TAG, mTime.toString());
            // attach new time to the activity arguments
            getArguments().putSerializable(EXTRA_TIME, mTime);
        }
    };

    public static TimePickerFragment newInstance(Date time) {
        Log.d(TAG, "newInstance");
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_TIME, time);

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(TAG, "onCreateDialog");
        mTime = (Date)getArguments().getSerializable(EXTRA_TIME);

        // create a calendar to get the year, month, and day
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mTime);
        //int year = calendar.get(Calendar.YEAR);
        //int month = calendar.get(Calendar.MONTH);
        //int day = calendar.get(Calendar.DAY_OF_MONTH);

        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_time, null);

        TimePicker timePicker = (TimePicker)v.findViewById(R.id.dialog_time_timePicker);
        timePicker.getCurrentHour();
        timePicker.getCurrentMinute();
        //TimePicker.init(year, month, day, mTimeChangedListener);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.time_picker_title)
                .setPositiveButton(
                        android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                sendResult(Activity.RESULT_OK);
                            }
                        })
                .create();
    }

    private void sendResult(int resultCode) {
        Log.d(TAG, "sendResult");
        if(getTargetFragment() == null) {
            return;
        }

        Intent i = new Intent();
        i.putExtra(EXTRA_TIME, mTime);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }
}