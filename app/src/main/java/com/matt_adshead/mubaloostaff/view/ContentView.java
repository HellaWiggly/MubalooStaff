package com.matt_adshead.mubaloostaff.view;

import android.support.annotation.IntDef;

import static com.matt_adshead.mubaloostaff.view.ContentView.ContentState.EMPTY;
import static com.matt_adshead.mubaloostaff.view.ContentView.ContentState.HAS_CONTENT;
import static com.matt_adshead.mubaloostaff.view.ContentView.ContentState.LOADING;

/**
 * Interface describing a view which has a content state according to {@link ContentState}
 *
 * @author matta
 * @date 05/03/2018
 */

public interface ContentView {
    @IntDef({LOADING, HAS_CONTENT, EMPTY})
    @interface ContentState {
        int LOADING = 0, HAS_CONTENT = 1, EMPTY = 2;
    }

    void setState(@ContentState int state);
}
