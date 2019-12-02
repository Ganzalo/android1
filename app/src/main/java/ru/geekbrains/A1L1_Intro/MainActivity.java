package ru.geekbrains.A1L1_Intro;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "States";

    public CheckBox humidityCheckBox;
    public CheckBox overcastCheckBox;
    public TextView humidityTextView;
    public TextView overcastTextView;

    public final static String HUMIDITY_STATE = "humidityCheckBox";
    public final static String OVERCAST_STATE = "overcastCheckBox";

    private MainActivityPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(getApplicationContext(), "onCreate()", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "MainActivity: onCreate()");
        init();
    }

    public void init() {
        humidityCheckBox = findViewById(R.id.humidityCheckBox);
        overcastCheckBox = findViewById(R.id.overcastCheckBox);
        humidityTextView = findViewById(R.id.valueHumidityTextView);
        overcastTextView = findViewById(R.id.valueOvercastTextView);
        presenter = new MainActivityPresenter(this);

        humidityCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.checkedView(view);
            }
        });

        overcastCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.checkedView(view);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(getApplicationContext(), "onStart()", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "MainActivity: onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(getApplicationContext(), "onResume()", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "MainActivity: onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(getApplicationContext(), "onPause()", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "MainActivity: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(getApplicationContext(), "onStop()", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "MainActivity: onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "onDestroy()", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "MainActivity: onDestroy()");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        boolean humidityState = humidityCheckBox.isChecked();
        boolean overcastState = overcastCheckBox.isChecked();
        outState.putBoolean(HUMIDITY_STATE, humidityState);
        outState.putBoolean(OVERCAST_STATE, overcastState);
        super.onSaveInstanceState(outState);
        Toast.makeText(getApplicationContext(), "onSaveInstanceState()", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "MainActivity: onSaveInstanceState()");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        humidityCheckBox.setChecked(savedInstanceState.getBoolean(HUMIDITY_STATE));
        overcastCheckBox.setChecked(savedInstanceState.getBoolean(OVERCAST_STATE));
        presenter.checkedView(humidityCheckBox);
        presenter.checkedView(overcastCheckBox);
        Toast.makeText(getApplicationContext(), "onRestoreInstanceState()", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "MainActivity: onRestoreInstanceState()");
    }

}
