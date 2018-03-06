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
    /**
     * Constants representing whether the view has loaded content yet,
     * and if it has whether it is empty.
     */
    @IntDef({LOADING, HAS_CONTENT, EMPTY})
    @interface ContentState {
        int LOADING = 0, HAS_CONTENT = 1, EMPTY = 2;
    }

    /**
     * Interface method. Implemented by Views. Sets the content state.
     *
     * @param state Content state.
     */
    void setState(@ContentState int state);
}
