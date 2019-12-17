package ru.geekbrains.A1L1_Intro.fragments;

import android.annotation.SuppressLint;
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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Objects;

import ru.geekbrains.A1L1_Intro.CoatContainer;
import ru.geekbrains.A1L1_Intro.R;
import ru.geekbrains.A1L1_Intro.recyclerview.DataClass;
import ru.geekbrains.A1L1_Intro.recyclerview.RecyclerViewAdapter;

public class WeatherInfoFragment extends Fragment {

    private TextView humidityTextView;
    private TextView overcastTextView;
    private CheckBox humidityCheckBox;
    private CheckBox overcastCheckBox;
    private RecyclerView tempRecyclerView;
    private RecyclerViewAdapter adapter;
    private MaterialButton saveButton;
    private TextInputEditText commentText;

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

        if (!Holder.contains(getCityName()))
            Holder.put(getCityName(), new Holder.Entry());

        TextView item = view.findViewById(R.id.cityTextView);
        item.setText(getCityName());

        ImageView imageView = view.findViewById(R.id.coatOfArmsImageView);
        TypedArray images = getResources().obtainTypedArray(R.array.coatofarms_imgs);
        imageView.setImageResource(images.getResourceId(getIndex(), -1));

        init(view);

        return view;
    }

    private void init(View view) {
        humidityCheckBox = view.findViewById(R.id.humidityCheckBox);
        overcastCheckBox = view.findViewById(R.id.overcastCheckBox);
        humidityTextView = view.findViewById(R.id.valueHumidityTextView);
        overcastTextView = view.findViewById(R.id.valueOvercastTextView);
        tempRecyclerView = view.findViewById(R.id.tempRecyclerView);
        saveButton = view.findViewById(R.id.saveButton);
        commentText = view.findViewById(R.id.commentText);

        humidityCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean state = ((CheckBox) view).isChecked();
                humidityTextView.setVisibility(visibleView(state));
                Holder.get(getCityName()).humidity = state;
            }
        });

        overcastCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean state = ((CheckBox) view).isChecked();
                overcastTextView.setVisibility(visibleView(state));
                Holder.get(getCityName()).overcast = state;
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String prevComment = Holder.get(getCityName()).comment;
                Holder.get(getCityName()).comment = Objects.requireNonNull(commentText.getText()).toString();
                Snackbar.make(saveButton, "Сохранение комментария", Snackbar.LENGTH_LONG) .setAction("Отмена", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Holder.get(getCityName()).comment = prevComment;
                    }
                }).show();
            }
        });

        overcastCheckBox.setChecked(Holder.get(getCityName()).overcast);
        humidityCheckBox.setChecked(Holder.get(getCityName()).humidity);
        commentText.setText(Holder.get(getCityName()).comment);
        overcastTextView.setVisibility(visibleView(overcastCheckBox.isChecked()));
        humidityTextView.setVisibility(visibleView(humidityCheckBox.isChecked()));

        fillRecyclerView();
    }

    private int visibleView(Boolean visible) {
        return visible ? View.VISIBLE : View.INVISIBLE;
    }

    private void fillRecyclerView() {
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

}
