package ru.geekbrains.A1L1_Intro.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.Objects;

import ru.geekbrains.A1L1_Intro.CoatContainer;
import ru.geekbrains.A1L1_Intro.WeatherInfoActivity;
import ru.geekbrains.A1L1_Intro.R;

public class CitiesFragment extends Fragment {
    private ListView listView;
    private TextView emptyTextView;

    private boolean isExistCoatOfArms;  // Можно ли расположить рядом фрагмент с гербом
    private int currentPosition = 0;    // Текущая позиция (выбранный город)

    // При создании фрагмента укажем его макет
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initList();
    }

    // activity создана, можно к ней обращаться. Выполним начальные действия
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Определение, можно ли будет расположить рядом герб в другом фрагменте
        isExistCoatOfArms = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;

        // Если это не первое создание, то восстановим текущую позицию
        if (savedInstanceState != null) {
            // Восстановление текущей позиции.
            currentPosition = savedInstanceState.getInt("CurrentCity", 0);
        }

        // Если можно нарисовать рядом герб, то сделаем это
        if (isExistCoatOfArms) {
            listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            showCoatOfArms();
        }
    }

    // Сохраним текущую позицию (вызывается перед выходом из фрагмента)
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("CurrentCity", currentPosition);
        super.onSaveInstanceState(outState);
    }

    private void initViews(View view) {
        listView = view.findViewById(R.id.cities_list_view);
        emptyTextView = view.findViewById(R.id.cities_list_empty_view);
    }

    private void initList() {
        // Для того, чтобы показать список, надо задействовать адаптер.
        // Такая конструкция работает для списков, например ListActivity.
        // Здесь создаем из ресурсов список городов (из массива)
        ArrayAdapter adapter =
                ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()), R.array.cities,
                android.R.layout.simple_list_item_activated_1);
        listView.setAdapter(adapter);

        listView.setEmptyView(emptyTextView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentPosition = position;
                showCoatOfArms();
            }
        });
    }

    // Показать герб. Ecли возможно, то показать рядом со списком,
    // если нет, то открыть вторую activity
    private void showCoatOfArms() {
        if (isExistCoatOfArms) {
            // Выделим текущий элемент списка
            listView.setItemChecked(currentPosition, true);

            // Проверим, что фрагмент с гербом существует в activity
            WeatherInfoFragment detail = (WeatherInfoFragment)
                    Objects.requireNonNull(getFragmentManager()).findFragmentById(R.id.coat_of_arms);

            // Если есть необходимость, то выведем герб
            if (detail == null || detail.getIndex() != currentPosition) {
                // Создаем новый фрагмент с текущей позицией для вывода герба

                detail = WeatherInfoFragment.create(getCoatContainer());

                // Выполняем транзакцию по замене фрагмента
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.coat_of_arms, detail);  // замена фрагмента
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                //ft.addToBackStack(null);
                ft.addToBackStack("Some_Key");
                ft.commit();
            }
        } else {
            // Если нельзя вывести герб рядом, откроем вторую activity
            Intent intent = new Intent();
            intent.setClass(Objects.requireNonNull(getActivity()), WeatherInfoActivity.class);
            // и передадим туда параметры
            intent.putExtra("index", getCoatContainer());
            startActivity(intent);
        }
    }

    private CoatContainer getCoatContainer() {
        String[] cities = getResources().getStringArray(R.array.cities);
        CoatContainer container = new CoatContainer();
        container.position = currentPosition;
        container.cityName = cities[currentPosition];
        return container;
    }
}
