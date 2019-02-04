package com.vatsaltechnosoft.mani.amritha.registrationloginsample;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MyDialogFragment.UserNameListener {

    private final AppCompatActivity activity = MainActivity.this;

    NestedScrollView nestedScrollView;

    AppCompatButton login;

    TextInputEditText userName;

    TextInputEditText password;

    TextInputLayout userLayout;

    TextInputLayout passwordLayout;

    AppCompatTextView registerText, forgetPassword;

    InputValidation inputValidation;

    DatabaseHelper databaseHelper;

    /*ProgressDialog mProgressDialog;*/

    String changedPassword = "";

    Intent i, intent;

    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        /* mProgressDialog = new ProgressDialog(MainActivity.this);*/

        pref = getSharedPreferences("user_details", MODE_PRIVATE);

        initViews();
//        initListeners();
        initObjects();

    }

    /**
     * This method is to initialize views
     */

    private void initViews() {

        nestedScrollView = findViewById(R.id.nestedScrollView);

        userName = findViewById(R.id.login_username_editText);

        password = findViewById(R.id.login_password_editText);

        login = findViewById(R.id.login_submit_button);

        userLayout = findViewById(R.id.textInputLayoutUser);

        passwordLayout = findViewById(R.id.textInputLayoutPassword);

        registerText = findViewById(R.id.signup_button);

        forgetPassword = findViewById(R.id.password_button);
    }
    /*
     *//**
     * This method is to initialize listeners
     *//*

    private void initListeners() {

        login.setOnClickListener(this);
        registerText.setOnClickListener(this);

    }*/

    /**
     * This method is to initialize objects to be used
     */

    private void initObjects() {
        inputValidation = new InputValidation(activity);

        databaseHelper = new DatabaseHelper(activity);
    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */

    public void onClick(View v) {

        // close existing dialog fragments
        FragmentManager manager = getFragmentManager();
        Fragment frag = manager.findFragmentByTag("fragment_change_password");
        if (frag != null) {
            manager.beginTransaction().remove(frag).commit();
        }

        switch (v.getId()) {

            case R.id.login_submit_button:
                verifyFromSqLite();
                break;

            case R.id.signup_button:
                i = new Intent(MainActivity.this, SignUp.class);
                startActivity(i);
                break;

            case R.id.password_button:
                MyDialogFragment fragmentChangePassword = new MyDialogFragment();
                fragmentChangePassword.show(manager, "fragment_change_password");
                break;

        }

    }

   /* private void startProgress() {

        new AsyncTask<Integer, Long, Boolean>() {
            @Override
            protected Boolean doInBackground(Integer... params) {
                mProgressDialog.setMax(params[0]);
                mProgressDialog.setMessage("Opening Register page");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                publishProgress(0L);

                long startTime = System.currentTimeMillis();
                long waitTime = params[0] * 1000;
                try {
                    while (System.currentTimeMillis() - startTime < waitTime) {
                        Thread.sleep(500);
                        publishProgress(System.currentTimeMillis() - startTime);
                    }
                } catch (Exception e) {
                    return false;
                }
                return true;
            }

            @Override
            protected void onProgressUpdate(Long... values) {
                if (values[0] == 0) {
                    mProgressDialog.show();
                } else {
                    mProgressDialog.setProgress((int) (values[0] / 1000));
                }
            }

            @Override
            protected void onPostExecute(Boolean result) {
                mProgressDialog.dismiss();
                Intent i = new Intent(MainActivity.this, SignUp.class);
                startActivityForResult(i, 0);
            }
        }.execute(3);

    }*/

    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */

    private void verifyFromSqLite() {
        /*i.getIntExtra("id",0);*/

        if (!inputValidation.isInputEditTextFilled(userName, userLayout, "Enter valid Name ")) {
            return;
        }

        if (!inputValidation.isInputEditTextFilled(password, passwordLayout, "Enter valid Password ")) {
            return;
        }

        if (databaseHelper.checkUser(userName.getText().toString().trim(), password.getText().toString().trim())) {



           /* new AsyncTask<Integer, Long, Boolean>() {
                @Override
                protected Boolean doInBackground(Integer... params) {
                    mProgressDialog.setMax(params[0]);
                    mProgressDialog.setMessage("Opening List Page");
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    publishProgress(0L);

                    long startTime = System.currentTimeMillis();
                    long waitTime = params[0] * 1000;
                    try {
                        while (System.currentTimeMillis() - startTime < waitTime) {
                            Thread.sleep(500);
                            publishProgress(System.currentTimeMillis() - startTime);
                        }
                    } catch (Exception e) {
                        return false;
                    }
                    return true;
                }

                @Override
                protected void onProgressUpdate(Long... values) {
                    if (values[0] == 0) {
                        mProgressDialog.show();
                    } else {
                        mProgressDialog.setProgress((int) (values[0] / 1000));
                    }
                }

                @Override
                protected void onPostExecute(Boolean result) {
                    mProgressDialog.dismiss();
                    Intent i = new Intent(MainActivity.this, ListActivity.class);
                    i.putExtra("USER NAME", userName.getText().toString().trim());
                    emptyInputEditText();
                    startActivityForResult(i, 0);
                }
            }.execute(3);*/

            /*i.putExtra("USER NAME", userName.getText().toString().trim());*/
            /*emptyInputEditText();*/

            SharedPreferences.Editor editor = pref.edit();
            editor.putString("username", userName.getText().toString().trim());
            editor.putString("password", password.getText().toString().trim());
            editor.commit();

            intent = new Intent(MainActivity.this, ListActivity.class);

            if (pref.contains("username") && pref.contains("password")) {
                startActivity(intent);
            }

            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
        } else {
            // Snack Bar to show success message that record is wrong
            Snackbar.make(nestedScrollView, "Not valid user name or password", Snackbar.LENGTH_LONG).show();
        }

    }

    /**
     * This method is to empty all input edit text
     */

    private void emptyInputEditText() {

        userName.setText(null);
        password.setText(null);

    }

    @Override
    public void onFinishUserDialog(String user, String password) {

        changedPassword = password;

        if (databaseHelper.checkUser(user)) {
            databaseHelper.updatePassword(user, changedPassword);
            Toast.makeText(activity, "Password changed successfully", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(activity, "Enter valid User Name", Toast.LENGTH_LONG).show();
        }

    }
}
