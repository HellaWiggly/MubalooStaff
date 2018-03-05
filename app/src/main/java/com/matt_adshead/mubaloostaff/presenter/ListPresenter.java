package com.matt_adshead.mubaloostaff.presenter;

import android.app.Activity;

import com.matt_adshead.mubaloostaff.view.activity.IListView;

/**
 * Presenter for the List Activity.
 *
 * @author Matt
 * @date 05/03/2018
 */
public class ListPresenter extends BasePresenter implements IListPresenter {

    private IListView view;

    public ListPresenter(IListView view) {
        this.view = view;
    }
}
