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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;

/**
 * Created by mia on 3/26/15.
 */
public class LoopFragment extends Fragment {
    private Loop mLoop;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mRecurringCheckBox;
    public static final String EXTRA_LOOP_ID = "com.example.mia.loop.loop_id";
    private static final String TAG = "LoopFragment";
    private static final String DIALOG_DATE = "date";
    private static final int REQUEST_DATE = 0;

    public void updateDate(){
        mDateButton.setText(mLoop.getDate().toString());
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
        Log.d(TAG, "newInstance" + EXTRA_LOOP_ID);
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
               DatePickerFragment dialog = DatePickerFragment.newInstance(mLoop.getDate());
               dialog.setTargetFragment(LoopFragment.this, REQUEST_DATE);
               dialog.show(fm, DIALOG_DATE);
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
           mLoop.setDate(date);
           updateDate();
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