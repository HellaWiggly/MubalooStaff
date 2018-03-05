package com.matt_adshead.mubaloostaff.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.matt_adshead.mubaloostaff.R;
import com.matt_adshead.mubaloostaff.presenter.IListPresenter;
import com.matt_adshead.mubaloostaff.presenter.ListPresenter;

public class ListActivity extends AppCompatActivity implements IListView {

    private IListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
    }

    private ListPresenter createPresenter() {
        return new ListPresenter(this);
    }
}
