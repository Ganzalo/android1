package ru.geekbrains.A1L1_Intro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class InfoActivity extends AppCompatActivity {

    TextView cityTextView;
    ImageView coatOfArmsImageView;
    TextView valueHumidityTextView;
    TextView valueOvercastTextView;
    Button moreDetailsButton;

    private static Map<String, Integer> resources = new HashMap<>();
    private static final String URL = "https://ru.wikipedia.org/wiki/";

    static {
        resources.put("Москва", R.drawable.moscow);
        resources.put("Санкт-Петербург", R.drawable.saint_petersburg);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        init();
        setValue();
    }

    public void init() {
        cityTextView = findViewById(R.id.cityTextView);
        coatOfArmsImageView = findViewById(R.id.coatOfArmsImageView);
        valueHumidityTextView = findViewById(R.id.valueHumidityTextView);
        valueOvercastTextView = findViewById(R.id.valueOvercastTextView);
        moreDetailsButton = findViewById(R.id.moreDetailsButton);

        moreDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(URL + cityTextView.getText().toString());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

    }

    private void setValue() {
        Serializable serializable = getIntent().getSerializableExtra(MainActivity.KEY_TO_DATA);
        if(serializable != null) {
            Parcel parcel = (Parcel) serializable;
            String city = Objects.requireNonNull(parcel).city;
            cityTextView.setText(city);
            valueHumidityTextView.setVisibility(visibleView(Objects.requireNonNull(parcel).humidity));
            valueOvercastTextView.setVisibility(visibleView(Objects.requireNonNull(parcel).overcast));
            coatOfArmsImageView.setImageResource(Objects.requireNonNull(resources.get(city)));
        }
    }

    private int visibleView(Boolean visible) {
        return visible ? View.VISIBLE : View.INVISIBLE;
    }

}
