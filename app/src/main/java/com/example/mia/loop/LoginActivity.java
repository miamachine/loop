package com.example.mia.loop;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


public class LoginActivity extends SingleFragmentActivity {
    private static final String TAG ="LoginActivity";
    private EditText mUsername;
    private EditText mPassword;

    @Override
    protected Fragment createFragment() {
        return new LoginFragment();
    }
}
