package com.matt_adshead.mubaloostaff.view.listener;

import android.support.v7.widget.RecyclerView;

/**
 * Click listener for {@link RecyclerView.Adapter} implementations.
 * Listens for clicks on a list item and receives the bound entity when they occur.
 *
 * @author matta
 * @date 05/03/2018
 */

public interface OnItemClickListener<T> {
    void onItemClick(T item);
}
