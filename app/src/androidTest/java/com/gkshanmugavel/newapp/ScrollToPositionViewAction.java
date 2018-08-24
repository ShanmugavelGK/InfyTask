package com.gkshanmugavel.newapp;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Matcher;

import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.core.AllOf.allOf;

public class ScrollToPositionViewAction implements ViewAction {
    private final int position;

    ScrollToPositionViewAction(int position) {
        this.position = position;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Matcher<View> getConstraints() {
        return allOf(isAssignableFrom(RecyclerView.class), isDisplayed());
    }

    @Override
    public String getDescription() {
        return "smooth scroll RecyclerView to position: " + position;
    }

    @Override
    public void perform(UiController uiController, View view) {
        RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.smoothScrollToPosition(position);
    }
}