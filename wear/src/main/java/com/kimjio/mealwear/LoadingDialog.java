package com.kimjio.mealwear;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by kimji on 2017-11-13.
 */

public class LoadingDialog extends AlertDialog {

    ImageView imageView;
    TextView loadingText;

    public LoadingDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        setCancelable(false);
        imageView = findViewById(R.id.loadingAnima);
        loadingText = findViewById(R.id.loadingPercentage);
    }

    public void setProgress(int value) {
        if (loadingText != null) {
            loadingText.setText(String.format(getContext().getString(R.string.loading_percentage), value));
        }
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
