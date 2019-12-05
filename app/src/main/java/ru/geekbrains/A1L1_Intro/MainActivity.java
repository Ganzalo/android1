package ru.geekbrains.A1L1_Intro;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "States";

    CheckBox humidityCheckBox;
    CheckBox overcastCheckBox;
    Spinner citiesSpinner;
    Button showWeatherBtn;

    final static String KEY_TO_DATA = "KEY_TO_DATA";
    private final static String CITY_STATE = "cityState";
    private final static String HUMIDITY_STATE = "humidityState";
    private final static String OVERCAST_STATE = "overcastState";

    private final String[] cities = {"Москва", "Санкт-Петербург"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "MainActivity: onCreate()");
        init();
    }

    public void init() {
        citiesSpinner = findViewById(R.id.citiesSpinner);
        humidityCheckBox = findViewById(R.id.humidityCheckBox);
        overcastCheckBox = findViewById(R.id.overcastCheckBox);
        showWeatherBtn = findViewById(R.id.showWeather);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.preview_text, cities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citiesSpinner.setAdapter(adapter);


        showWeatherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                ActivityInfo activityInfo =
                        intent.resolveActivityInfo(getPackageManager(),
                                intent.getFlags());
                if (activityInfo != null) {
                    Parcel parcel = new Parcel();
                    parcel.city = citiesSpinner.getSelectedItem().toString();
                    parcel.humidity = humidityCheckBox.isChecked();
                    parcel.overcast = overcastCheckBox.isChecked();
                    intent.putExtra(KEY_TO_DATA, parcel);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "MainActivity: onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "MainActivity: onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "MainActivity: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "MainActivity: onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "MainActivity: onDestroy()");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        citiesSpinner.getSelectedItemPosition();
        outState.putInt(CITY_STATE, citiesSpinner.getSelectedItemPosition());
        outState.putBoolean(HUMIDITY_STATE, humidityCheckBox.isChecked());
        outState.putBoolean(OVERCAST_STATE, overcastCheckBox.isChecked());
        super.onSaveInstanceState(outState);
        Log.d(TAG, "MainActivity: onSaveInstanceState()");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        citiesSpinner.setSelection(savedInstanceState.getInt(CITY_STATE));
        humidityCheckBox.setChecked(savedInstanceState.getBoolean(HUMIDITY_STATE));
        overcastCheckBox.setChecked(savedInstanceState.getBoolean(OVERCAST_STATE));
        Log.d(TAG, "MainActivity: onRestoreInstanceState()");
    }



}
