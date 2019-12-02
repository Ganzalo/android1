package ru.geekbrains.A1L1_Intro;

import android.view.View;

public final class MainActivityPresenter {

    private MainActivity view;

    public MainActivityPresenter(MainActivity view) {
        this.view = view;
    }

    public void showView(View clickView) {
        if (clickView.equals(view.humidityCheckBox))
            view.humidityTextView.setVisibility(View.VISIBLE);
        else
            view.overcastTextView.setVisibility(View.VISIBLE);
    }

    public void hideView(View clickView) {
        if (clickView.equals(view.humidityCheckBox))
            view.humidityTextView.setVisibility(View.INVISIBLE);
        else
            view.overcastTextView.setVisibility(View.INVISIBLE);
    }
}
