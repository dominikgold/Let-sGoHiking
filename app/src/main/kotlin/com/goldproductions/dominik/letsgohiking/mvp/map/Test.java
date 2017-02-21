package com.goldproductions.dominik.letsgohiking.mvp.map;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.goldproductions.dominik.letsgohiking.R;

import org.jetbrains.annotations.Nullable;

public class Test extends AppCompatActivity {

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
