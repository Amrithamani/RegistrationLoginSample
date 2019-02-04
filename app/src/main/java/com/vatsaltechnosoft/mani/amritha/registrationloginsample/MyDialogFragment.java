package com.vatsaltechnosoft.mani.amritha.registrationloginsample;

import android.app.DialogFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MyDialogFragment extends DialogFragment implements View.OnClickListener {

    EditText userName, passwordChange;

    Button save, cancel;

    // Empty constructor required for DialogFragment
    public MyDialogFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_dialog_fragment, container);

        initViews(view);

        initListener();

        return view;
    }

    private void initViews(View view) {

        userName = view.findViewById(R.id.userName);

        passwordChange = view.findViewById(R.id.changePassword);

        save = view.findViewById(R.id.save_button);

        cancel = view.findViewById(R.id.cancel_button);
    }

    private void initListener() {
        save.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.save_button:
                saveClick();
                break;

            case R.id.cancel_button:
                this.dismiss();
                break;
        }

    }

    private void saveClick() {

        if (passwordChange.getText().toString().length() >= 6) {
            // Return input text to activity
            UserNameListener activity = (UserNameListener) getActivity();
            activity.onFinishUserDialog(userName.getText().toString(), passwordChange.getText().toString());
            this.dismiss();
        } else {
            Toast.makeText(getActivity(), "Password minimum 6", Toast.LENGTH_SHORT).show();
        }
    }

    public interface UserNameListener {
        void onFinishUserDialog(String User, String password);
    }
}

