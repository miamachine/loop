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

import java.util.Date;
import java.util.UUID;

/**
 * Created by mia on 3/29/15.
 */
public class LoopCreateFragment extends Fragment {
    private Loop mLoop;
    private EditText mTitleField;
    private Button mDateButton;
    private Button mTimeButton;
    private CheckBox mRecurringCheckBox;
    private Spinner mCategorySpinner;
    public static final String EXTRA_LOOP_ID = "com.example.mia.loop.loop_id";
    private static final String TAG = "LoopCreateFragment";
    private static final String DIALOG_DATE = "date";
    private static final int REQUEST_DATE = 0;
    private static final String DIALOG_TIME = "time";
    private static final int REQUEST_TIME = 0;

    public void updateDate(){
        mDateButton.setText(mLoop.getDateAndTime().toString());
    }

    public void updateTime() {
        mTimeButton.setText(mLoop.getDateAndTime().toString());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        UUID loopId = (UUID)getActivity().getIntent().getSerializableExtra(LoopCreateFragment.EXTRA_LOOP_ID);
        mLoop = Loops.get(getActivity()).getLoop(loopId);

        setHasOptionsMenu(true);
    }

    public static LoopCreateFragment newInstance(UUID loopId) {
        Log.d(TAG, "newInstance");
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_LOOP_ID, loopId);

        LoopCreateFragment fragment = new LoopCreateFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View v = inflater.inflate(R.layout.fragment_loop_create, parent, false);

        mTitleField = (EditText)v.findViewById(R.id.loop_title);
        mTitleField.setText(mLoop.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mLoop.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDateButton = (Button)v.findViewById(R.id.loop_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mLoop.getDateAndTime());
                dialog.setTargetFragment(LoopCreateFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });

        mTimeButton = (Button)v.findViewById(R.id.loop_time);
        updateTime();
        mTimeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                TimePickerFragment dialog = TimePickerFragment.newInstance(mLoop.getDateAndTime());
                dialog.setTargetFragment(LoopCreateFragment.this, REQUEST_TIME);
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

        // special category-dependent attributes
        mCategorySpinner = (Spinner)v.findViewById(R.id.loop_category_spinner);
        mCategorySpinner.setAdapter(new ArrayAdapter<LoopCategoryType>(getActivity(), android.R.layout.simple_spinner_item, LoopCategoryType.values()));
        mCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected, position :" + position + ", id: " + id);
                LoopCategoryType category = (LoopCategoryType)mCategorySpinner.getSelectedItem();
                mLoop.setCategoryType(category);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "onNothingSelected");
            }
        });

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
            Date time = (Date)data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            Log.d(TAG, time.toString());
            mLoop.setDateAndTime(time);
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
