package com.matt_adshead.mubaloostaff.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.matt_adshead.mubaloostaff.R;

/**
 * Loading spinner and text custom view.
 *
 * @author matta
 * @date 05/03/2018
 */

public class LoadingSpinner extends RelativeLayout {

    private TextView    textView;
    private ProgressBar progressBar;

    public LoadingSpinner(Context context) {
        super(context);
        init(null);
    }

    public LoadingSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        inflate(getContext(), R.layout.loading_spinner, this);

        textView    = findViewById(R.id.loading_message);
        progressBar = findViewById(R.id.progress_bar);
    }

    public void setMessage(String message) {
        textView.setText(message);
    }
}
