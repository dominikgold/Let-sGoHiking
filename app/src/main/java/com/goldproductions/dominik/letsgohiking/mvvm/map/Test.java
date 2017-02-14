package com.goldproductions.dominik.letsgohiking.mvvm.map;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.goldproductions.dominik.letsgohiking.R;
import com.goldproductions.dominik.letsgohiking.mvvm.base.BaseActivity;

import org.jetbrains.annotations.Nullable;

/**
 * Created by Dominik on 06.02.2017.
 */
public class Test extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Title");
        }

        new TestAsyncTask().execute("database name");
    }

    final class TestAsyncTask extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                // do database stuff
                return true;
            } catch (Exception e) {
                Log.e("", e.getMessage());
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            signalCompletion(aBoolean);
        }
    }

    private void signalCompletion(boolean success) {
        // do UI stuff
    }

}
