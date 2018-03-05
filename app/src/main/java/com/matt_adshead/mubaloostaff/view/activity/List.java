package com.matt_adshead.mubaloostaff.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.matt_adshead.mubaloostaff.R;
import com.matt_adshead.mubaloostaff.presenter.ListPresenter;

public class List extends AppCompatActivity {

    private ListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
    }
}
