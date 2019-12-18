package ru.geekbrains.A1L1_Intro.fragments;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import ru.geekbrains.A1L1_Intro.CoatContainer;
import ru.geekbrains.A1L1_Intro.R;
import ru.geekbrains.A1L1_Intro.WeatherDataLoader;
import ru.geekbrains.A1L1_Intro.recyclerview.DataClass;
import ru.geekbrains.A1L1_Intro.recyclerview.RecyclerViewAdapter;

public class WeatherInfoFragment extends Fragment {

    private final Handler handler = new Handler();

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

        initView(view);

        return view;
    }

    private void updateWeatherData(final String city) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final JSONObject jsonObject = WeatherDataLoader.getJSONData(getContext(), city);
                if (jsonObject == null)
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "нет данных", Toast.LENGTH_SHORT).show();
                        }
                    });
                else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            renderWeather(jsonObject);
                        }
                    });
                }
            }
        }).start();
    }

    private void renderWeather(JSONObject jsonObject) {
        try {
            JSONArray list = jsonObject.getJSONArray("list");
            List<DataClass> data = new ArrayList<>();
            for (int i = 0; i < list.length(); i = i + 8 - 1) {
                String date = parseDate(list.getJSONObject(i).getLong("dt"));
                String temp = parseTemp(list.getJSONObject(i).getJSONObject("main").getDouble("temp"));
                data.add(new DataClass(date, temp));
            }
            setParam(list.getJSONObject(0));
            fillRecyclerView(data);
        } catch (Exception exc) {
            Toast.makeText(getContext(), "Ошибка обработки данных", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SimpleDateFormat")
    private String parseDate(long date) {
        return new SimpleDateFormat("dd/MM").format(new Date(date * 1000));
    }

    private String parseTemp(double temp) {
        return (int) Math.round(temp) + "\u2103";
    }

    private void setParam(JSONObject object) {
        try {
            int clouds = object.getJSONObject("clouds").getInt("all");
            int humidity = object.getJSONObject("main").getInt("humidity");
            humidityTextView.setText(humidity + " %");
            overcastTextView.setText(clouds + " %");
        } catch (Exception exc) {
            Toast.makeText(getContext(), "Ошибка обработки доп данных", Toast.LENGTH_SHORT).show();
        }
    }


    private void initView(View view) {
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
                Snackbar.make(saveButton, "Сохранение комментария", Snackbar.LENGTH_LONG).setAction("Отмена", new View.OnClickListener() {
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

        updateWeatherData("Moscow");
    }

    private int visibleView(Boolean visible) {
        return visible ? View.VISIBLE : View.INVISIBLE;
    }

    private void fillRecyclerView(List<DataClass> data) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        adapter = new RecyclerViewAdapter(data);

        layoutManager.setOrientation(RecyclerView.HORIZONTAL);

        tempRecyclerView.setLayoutManager(layoutManager);
        tempRecyclerView.setAdapter(adapter);
    }

}
