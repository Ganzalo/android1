package ru.geekbrains.A1L1_Intro;

import android.view.View;
import android.widget.CheckBox;

public final class MainActivityPresenter {

    private MainActivity view;

    public MainActivityPresenter(MainActivity view) {
        this.view = view;
    }

    public void checkedView(View view) {
        if(((CheckBox) view).isChecked()){
            showView(view, View.VISIBLE);
        } else {
            showView(view, View.INVISIBLE);
        }
    }

    private void showView(View clickView, int visible) {
        if (clickView.equals(view.humidityCheckBox))
            view.humidityTextView.setVisibility(visible);
        else
            view.overcastTextView.setVisibility(visible);
    }

}
