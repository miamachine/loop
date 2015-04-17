package com.example.mia.loop;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by mia on 3/26/15.
 */
public class LoopFragment extends Fragment {
    private Loop mLoop;
    private TextView mTitleTextView;
    private Button mDateButton;
    private Button mTimeButton;
    private CheckBox mRecurringCheckBox;
    private TextView mCategoryTextView;
    public static final String EXTRA_LOOP_ID = "com.example.mia.loop.loop_id";
    private static final String TAG = "LoopFragment";
    private static final String DIALOG_DATE = "date";
    private static final int REQUEST_DATE = 0;
    private static final String DIALOG_TIME = "time";
    private static final int REQUEST_TIME = 0;


    public boolean isThisWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        calendar.setTime(date);
        int loopDayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        if(loopDayOfYear - dayOfYear < 6) {
            return true;
        }
        else {
            return false;
        }
    }

    public void updateDate(){
        if(isThisWeek(mLoop.getDateAndTime())) {
            // just print day of week
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
            String dayOfTheWeek = sdf.format(mLoop.getDateAndTime());
            mDateButton.setText(dayOfTheWeek);
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("E");
            String dayOfTheWeek = sdf.format(mLoop.getDateAndTime());
            // print abbreviated day of week and full date
            DateFormat df = DateFormat.getDateInstance();
            mDateButton.setText(sdf.format(mLoop.getDateAndTime()) + " " + df.format(mLoop.getDateAndTime()));
        }

    }

    public void updateTime() {
        DateFormat df = DateFormat.getTimeInstance(DateFormat.SHORT);
        mTimeButton.setText(df.format(mLoop.getDateAndTime()));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        UUID loopId = (UUID)getArguments().getSerializable(EXTRA_LOOP_ID);

        mLoop = Loops.get(getActivity()).getLoop(loopId);

        setHasOptionsMenu(true);
    }

    public static LoopFragment newInstance(UUID loopId) {
        Log.d(TAG, "newInstance");
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_LOOP_ID, loopId);

        LoopFragment fragment = new LoopFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View v = inflater.inflate(R.layout.fragment_loop, parent, false);

        mTitleTextView = (TextView)v.findViewById(R.id.loop_title);
        mTitleTextView.setText(mLoop.getTitle());

        mDateButton = (Button)v.findViewById(R.id.loop_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mLoop.getDateAndTime());
                dialog.setTargetFragment(LoopFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });

        mTimeButton = (Button)v.findViewById(R.id.loop_time);
        updateTime();
        mTimeButton.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
               FragmentManager fm = getActivity().getSupportFragmentManager();
               TimePickerFragment dialog = TimePickerFragment.newInstance(mLoop.getDateAndTime());
               dialog.setTargetFragment(LoopFragment.this, REQUEST_TIME);
               dialog.show(fm, DIALOG_TIME);
           }
        });

        mRecurringCheckBox = (CheckBox)v.findViewById(R.id.loop_recurring);
        mRecurringCheckBox.setChecked(mLoop.isRecurring());
        mRecurringCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // set the loop's recurring property
                mLoop.setRecurring(isChecked);
            }
        });

        mCategoryTextView = (TextView)v.findViewById(R.id.loop_category);
        mCategoryTextView.setText("Category: " + mLoop.getCategoryType());

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult");
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        if(requestCode == REQUEST_DATE) {
            Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mLoop.setDateAndTime(date);
            updateDate();
        }
        if(requestCode == REQUEST_TIME) {
            Date date = (Date)data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            mLoop.setDateAndTime(date);
            updateTime();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected");
        switch(item.getItemId()) {
            case android.R.id.home:
                if(NavUtils.getParentActivityName(getActivity()) != null) {
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}