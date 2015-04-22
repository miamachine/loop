package com.example.mia.loop;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by mia on 3/29/15.
 */
public class LoopCreateFragment extends Fragment {
    private Loop mLoop;
    private Loop.Detail mLoopDetail;
    private LinearLayout mLayout;
    private EditText mTitleField;
    private EditText mDetailField;
    private Button mDateButton;
    private Button mTimeButton;
    private ImageButton mAddFieldButton;
    private CheckBox mRecurringCheckBox;
    private Spinner mCategorySpinner;
    private Spinner mDetailTypeSpinner;
    //private FloatingActionButton mFAB;
    //private List<String> mEditTextList = new ArrayList<String>();
    public static final String EXTRA_LOOP_ID = "com.example.mia.loop.loop_id";
    private static final String TAG = "LoopCreateFragment";
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
        mTitleField.setTextColor(getResources().getColor(R.color.primary));
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
        mRecurringCheckBox.setTextColor(getResources().getColor(R.color.primary));
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

        mLayout = (LinearLayout)v.findViewById(R.id.loop_create);
        mDetailField = (EditText)v.findViewById(R.id.loop_detail);
        mDetailField.setTextColor(getResources().getColor(R.color.primary));
        //mDetailField.setText(mLoop.getDetailText());
        mDetailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "Detail text changed!");
                //mDetailField.setText(s.toString());
                //mLoopDetail.set
                mLoop.setDetailText(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mAddFieldButton = (ImageButton)v.findViewById(R.id.loop_add_detail);
        mAddFieldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Button clicked! " + mLoop.getDetail());
                String text = mLoop.getDetail();
                Log.d(TAG, "New detail: " + text);
                if(text != null) {
                    mLayout.addView(addEditTextView(mDetailField.getText().toString()));
                    //mLoop.setDetailText(mDetailField.getText().toString());
                    String toAdd = mDetailField.getText().toString();
                    Log.d(TAG, toAdd);
                    mLoop.addDetail();
                }
            }
        });

        mDetailTypeSpinner = (Spinner)v.findViewById(R.id.loop_detail_spinner);
        mDetailTypeSpinner.setAdapter(new ArrayAdapter<Loop.Detail>(getActivity(), android.R.layout.simple_spinner_item, Loop.Detail.values()));
        mDetailTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected, position :" + position + ", id: " + id);
                Loop.Detail detailType = (Loop.Detail)mDetailTypeSpinner.getSelectedItem();
                mLoop.setDetailType(detailType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "onNothingSelected");
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //v = getActivity().getLayoutInflater().inflate(R.layout.floating_action_button, parent, false);
            ListView listView = (ListView)v.findViewById(android.R.id.list);
            FloatingActionButton fab = (FloatingActionButton)v.findViewById(R.id.fab);
            fab.attachToListView(listView);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // save loop
                }
            });
        }


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
            case R.id.menu_item_save_loop:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private TextView addEditTextView(String text) {
        final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView(this.getActivity());
        textView.setLayoutParams(lp);
        textView.setText(text);
        mDetailField.setText("");
        return textView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_create, menu);
    }

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_loop:
                Loop loop = new Loop();
                Loops.get(getActivity()).addLoop(loop);
                Intent i = new Intent(getActivity(), LoopCreateActivity.class);
                i.putExtra(LoopCreateFragment.EXTRA_LOOP_ID, loop.getId());
                UUID id = loop.getId();
                Log.d(TAG, "loop.getId() " + id);
                startActivityForResult(i, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    */
}
