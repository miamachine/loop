package com.example.mia.loop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
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

import java.util.Calendar;
import java.util.UUID;

/**
 * Created by mia on 4/7/15.
 */
public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";
    public static final String EXTRA_USER_ID = "com.example.mia.loop.user_id";
    private Button mLoginButton;

    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    */

    public static LoginFragment newInstance(UUID userId) {
        Log.d(TAG, "newInstance");
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_USER_ID, userId);

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        UUID loopId = (UUID)getActivity().getIntent().getSerializableExtra(LoopCreateFragment.EXTRA_LOOP_ID);

        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View v = inflater.inflate(R.layout.activity_login, parent, false);

        mLoginButton = (Button)v.findViewById(R.id.loginBtn);
        //updateDate();
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                // start LoopPagerActivity with this loop
                Intent i = new Intent(getActivity(), LoopListActivity.class);
                //i.putExtra(LoopFragment.EXTRA_LOOP_ID, loop.getId());
                startActivity(i);
            }
        });

        return v;
    }
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }
    */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult");
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        /*
        if(requestCode == REQUEST_DATE_AND_TIME) {
            Calendar dateAndTime = (Calendar)data.getSerializableExtra(DateAndTimePickerFragment.EXTRA_DATE_AND_TIME);
            mLoop.setDateAndTime(dateAndTime);
            updateDateAndTime();
        }
        */
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
