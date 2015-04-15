package com.example.mia.loop;

import android.os.Build;
import android.support.v4.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by mia on 3/26/15.
 */
public class LoopListFragment extends ListFragment {
    private static final String TAG = "LoopListFragment";
    private ArrayList<Loop> mLoops;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        // returns the hosting activity and allows a fragment to handle more of
        // an activity's affairs
        getActivity().setTitle(R.string.loops_title);
        mLoops = Loops.get(getActivity()).getLoops();

        LoopAdapter adapter = new LoopAdapter(mLoops);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // get the loop from the adapter
        Loop loop = ((LoopAdapter)getListAdapter()).getItem(position);

        Log.d(TAG, "onListItemClick");
        // start LoopPagerActivity with this loop
        Intent i = new Intent(getActivity(), LoopPagerActivity.class);
        i.putExtra(LoopFragment.EXTRA_LOOP_ID, loop.getId());
        startActivity(i);
    }

    private class LoopAdapter extends ArrayAdapter<Loop> {
        public LoopAdapter(ArrayList<Loop> loops) {
            super(getActivity(), 0, loops);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.d(TAG, "getView");
            // if we weren't given a view, inflate one
            if(convertView == null) {
                convertView = getActivity()
                        .getLayoutInflater()
                        .inflate(R.layout.list_item_loop, null);
            }

            // configure the view for this loop
            Loop loop = getItem(position);

            TextView titleTextView = (TextView)convertView.findViewById(R.id.loop_list_item_titleTextView);
            int color = loop.getCategoryColor();
            titleTextView.setTextColor(getResources().getColor(R.color.primary));
            titleTextView.setText(loop.getTitle());

            TextView dateTextView = (TextView)convertView.findViewById(R.id.loop_list_item_dateTextView);
            dateTextView.setTextColor(getResources().getColor(R.color.primary_dark));
            dateTextView.setText(loop.getDate().toString());

            TextView categoryTextView = (TextView)convertView.findViewById(R.id.loop_list_item_categoryTextView);
            categoryTextView.setTextColor(getResources().getColor(color));
            categoryTextView.setText(loop.getCategoryType().toString());

            CheckBox recurringCheckBox = (CheckBox)convertView.findViewById(R.id.loop_list_item_recurringCheckBox);
            recurringCheckBox.setChecked(loop.isRecurring());

            return convertView;
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume");
        ((LoopAdapter)getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ((LoopAdapter)getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_loop_list, menu);
    }

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

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View v;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            v = getActivity().getLayoutInflater().inflate(R.layout.fragment_loop_list, parent, false);
            ListView listView = (ListView)v.findViewById(android.R.id.list);
            FloatingActionButton fab = (FloatingActionButton)v.findViewById(R.id.fab);
            fab.attachToListView(listView);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Loop loop = new Loop();
                    Loops.get(getActivity()).addLoop(loop);
                    Intent i = new Intent(getActivity(), LoopCreateActivity.class);
                    i.putExtra(LoopCreateFragment.EXTRA_LOOP_ID, loop.getId());
                    UUID id = loop.getId();
                    Log.d(TAG, "loop.getId() " + id);
                    startActivityForResult(i, 0);
                }
            });
        } else {
            v = super.onCreateView(inflater, parent, savedInstanceState);
        }
        return v;
    }
}
