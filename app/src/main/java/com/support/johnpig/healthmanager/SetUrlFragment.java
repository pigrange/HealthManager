package com.support.johnpig.healthmanager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

public class SetUrlFragment extends DialogFragment implements View.OnClickListener {

    private EditText setUrl;
    private OnOkClickListener listener;
    private static final String TAG = "SetUrlFragment";
    public void setOKClickListener(OnOkClickListener okClickListener){
        this.listener = okClickListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_set_url, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUrl = view.findViewById(R.id.edit_url);
        view.findViewById(R.id.setUrl_ok).setOnClickListener(this);
        view.findViewById(R.id.setUrl_cancel).setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window != null){
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = (int) (getResources().getDisplayMetrics().widthPixels*0.75);
            window.setAttributes(params);
        }else {
            Log.e(TAG,"onCreateDialog: window == null");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setUrl_ok:
                // TODO: 2018/5/14
                listener.OnOkClick(setUrl);
                break;
            case R.id.setUrl_cancel:
                // TODO: 2018/5/14
                dismiss();
                break;
            default:
                break;
        }

    }

    interface OnOkClickListener{
        void OnOkClick(EditText editText);
    }
}
