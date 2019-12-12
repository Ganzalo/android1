package ru.geekbrains.A1L1_Intro.fragments;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import ru.geekbrains.A1L1_Intro.CoatContainer;
import ru.geekbrains.A1L1_Intro.R;
import ru.geekbrains.A1L1_Intro.recyclerview.DataClass;
import ru.geekbrains.A1L1_Intro.recyclerview.RecyclerViewAdapter;

public class WeatherInfoFragment extends Fragment {
    private TextView cityTextView;
    private TextView humidityTextView;
    private TextView overcastTextView;
    private CheckBox humidityCheckBox;
    private CheckBox overcastCheckBox;
    private RecyclerView tempRecyclerView;
    private RecyclerViewAdapter adapter;

    static WeatherInfoFragment create(CoatContainer container) {
        WeatherInfoFragment fragment = new WeatherInfoFragment();    // создание

        // Передача параметра
        Bundle args = new Bundle();
        args.putSerializable("index", container);
        fragment.setArguments(args);
        return fragment;
    }

    // Получить индекс из списка (фактически из параметра)
    int getIndex() {
        CoatContainer coatContainer = (CoatContainer) (Objects.requireNonNull(getArguments())
                .getSerializable("index"));

        try {
            return Objects.requireNonNull(coatContainer).position;
        } catch (Exception e) {
            return 0;
        }
    }

    private String getCityName() {
        CoatContainer coatContainer = (CoatContainer) (Objects.requireNonNull(getArguments())
                .getSerializable("index"));

        try {
            return Objects.requireNonNull(coatContainer).cityName;
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    @SuppressLint("Recycle")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_weather_info, null);
        init(view);

        TextView item = view.findViewById(R.id.cityTextView);
        item.setText(getCityName());

        ImageView imageView = view.findViewById(R.id.coatOfArmsImageView);
        TypedArray images = getResources().obtainTypedArray(R.array.coatofarms_imgs);
        imageView.setImageResource(images.getResourceId(getIndex(), -1));

        return view;
    }

    private void init(View view) {
        humidityCheckBox = view.findViewById(R.id.humidityCheckBox);
        overcastCheckBox = view.findViewById(R.id.overcastCheckBox);
        humidityTextView = view.findViewById(R.id.valueHumidityTextView);
        overcastTextView = view.findViewById(R.id.valueOvercastTextView);
        tempRecyclerView = view.findViewById(R.id.tempRecyclerView);
        cityTextView = view.findViewById(R.id.cityTextView);

        humidityCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int visibility = visibleView(((CheckBox) view).isChecked());
                humidityTextView.setVisibility(visibility);
            }
        });

        overcastCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int visibility = visibleView(((CheckBox) view).isChecked());
                overcastTextView.setVisibility(visibility);
            }
        });
        initRecyclerView();
    }

    private int visibleView(Boolean visible) {
        return visible ? View.VISIBLE : View.INVISIBLE;
    }

    private void initRecyclerView() {
        String[] dates = getResources().getStringArray(R.array.date);
        String[] temperatures = getResources().getStringArray(R.array.temperatures);

        ArrayList<DataClass> list = new ArrayList<>(dates.length);

        for (int i = 0; dates.length > i; i++)
            list.add(new DataClass(dates[i], temperatures[i]));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        adapter = new RecyclerViewAdapter(list);

        layoutManager.setOrientation(RecyclerView.HORIZONTAL);

        tempRecyclerView.setLayoutManager(layoutManager);
        tempRecyclerView.setAdapter(adapter);
    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//       // Toast.makeText(getContext(), "tut", Toast.LENGTH_SHORT).show();
//        if (savedInstanceState != null) {
//            Parcel parcel = (Parcel) savedInstanceState.getSerializable("Test");
//            //Toast.makeText(getContext(), parcel.humidity + " " + parcel.overcast, Toast.LENGTH_SHORT).show();
//            if (parcel != null) {
//                humidityTextView.setVisibility(visibleView(parcel.humidity));
//                overcastTextView.setVisibility(visibleView(parcel.overcast));
//            }
//        }
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        Parcel parcel = new Parcel();
//        parcel.humidity = humidityCheckBox.isChecked();
//        parcel.overcast = overcastCheckBox.isChecked();
//        //Toast.makeText(getContext(), cityTextView.getText().toString() + " " + humidityCheckBox.isChecked(), Toast.LENGTH_SHORT).show();
//        //outState.putSerializable(cityTextView.getText().toString(), parcel);
//        outState.putSerializable("Test", parcel);
//    }

}
