package ru.geekbrains.A1L1_Intro.fragments;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import  ru.geekbrains.A1L1_Intro.CoatContainer;
import  ru.geekbrains.A1L1_Intro.R;

import java.util.Objects;

public class CoatOfArmsFragment extends Fragment {
    static CoatOfArmsFragment create(CoatContainer container) {
        CoatOfArmsFragment fragment = new CoatOfArmsFragment();    // создание

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
            return coatContainer.position;
        } catch (Exception e) {
            return 0;
        }
    }

    String getCityName() {
        CoatContainer coatContainer = (CoatContainer) (Objects.requireNonNull(getArguments())
                .getSerializable("index"));

        try {
            return coatContainer.cityName;
        } catch (Exception e) {
            return "";
        }
    }

//    @Override
//    @SuppressLint("Recycle")
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        LinearLayout layout = new LinearLayout(getContext());
//        layout.setOrientation(LinearLayout.VERTICAL);
//
//        TextView cityNameTextView = new TextView(getContext());
//        String cityName = getCityName();
//        cityNameTextView.setText(cityName);
//
//        // Определить какой герб надо показать, и показать его
//        ImageView coatOfArms = new ImageView(getActivity());
//
//        // Получить из ресурсов массив указателей на изображения гербов
//         TypedArray images = getResources().obtainTypedArray(R.array.coatofarms_imgs);
//        // Выбрать по индексу подходящий
//        coatOfArms.setImageResource(images.getResourceId(getIndex(), -1));
//
//        layout.addView(cityNameTextView);
//        layout.addView(coatOfArms);
//
//        return layout;     // Вместо макета используем сразу картинку
//    }

    @Override
    @SuppressLint("Recycle")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_test, null);

        TextView item = view.findViewById(R.id.cityTextView);
        item.setText(getCityName());

        ImageView imageView = view.findViewById(R.id.coatOfArmsImageView);
        TypedArray images = getResources().obtainTypedArray(R.array.coatofarms_imgs);
        imageView.setImageResource(images.getResourceId(getIndex(), -1));

        return view;
    }
}
