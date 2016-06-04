package com.gendigital.tareacontacto;

import android.content.Intent;
import android.os.Bundle;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.inputmethod.EditorInfo;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class RegistroActivity extends AppCompatActivity  {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private TextInputEditText inputName, inputPhone, inputEmail, inputDescrip;
    private TextInputLayout inputLayoutName, inputLayoutPhone, inputLayoutEmail, inputLayoutDescrip;
    private Button btnNext;
    private DatePicker dpBirthday;

    private int year;
    private int month;
    private int day;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutPhone = (TextInputLayout) findViewById(R.id.input_layout_phone);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutDescrip = (TextInputLayout) findViewById(R.id.input_layout_descrip);
        inputName = (TextInputEditText) findViewById(R.id.input_name);
        inputPhone = (TextInputEditText) findViewById(R.id.input_phone);
        inputEmail = (TextInputEditText) findViewById(R.id.input_email);
        inputDescrip = (TextInputEditText) findViewById(R.id.input_descrip);
        btnNext = (Button) findViewById(R.id.btn_next);

        inputName.addTextChangedListener(new MyTextWatcher(inputName));
        inputPhone.addTextChangedListener(new MyTextWatcher(inputPhone));
        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputDescrip.addTextChangedListener(new MyTextWatcher(inputDescrip));

        setCurrentDate();

        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
    }
    // display current date
    public void setCurrentDate() {

        dpBirthday = (DatePicker) findViewById(R.id.dpBirthday);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        // set current date into datepicker
        dpBirthday.init(year, month, day, null);

    }
    public static Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    /**
     * Validating form
     */
    private void submitForm() {

        if (!validateName()) {
            return;
        }

        if (!validatePhone()) {
            return;
        }

        if (!validateEmail()) {
            return;
        }

        if (!validateDescrip()) {
            return;
        }

        Toast.makeText(getApplicationContext(), "Transfiriendo datos...", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(this, ConfirmaActivity.class );
        i.putExtra("Name", inputName.getText().toString());
        i.putExtra("BirthdayDay", dpBirthday.getDayOfMonth());
        i.putExtra("BirthdayMonth", dpBirthday.getMonth());
        i.putExtra("BirthdayYear", dpBirthday.getYear());
        i.putExtra("Phone", inputPhone.getText().toString());
        i.putExtra("Email", inputEmail.getText().toString());
        i.putExtra("Descrip", inputDescrip.getText().toString());
        startActivity(i);

    }

    private boolean validateName() {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            inputName.requestFocus();
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePhone() {
        if (inputPhone.getText().toString().trim().isEmpty()) {
            inputLayoutPhone.setError(getString(R.string.err_msg_phone));
            inputPhone.requestFocus();
            return false;
        } else {
            inputLayoutPhone.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            inputEmail.requestFocus();
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateDescrip() {
        if (inputDescrip.getText().toString().trim().isEmpty()) {
            inputLayoutDescrip.setError(getString(R.string.err_msg_descrip));
            inputDescrip.requestFocus();
            return false;
        } else {
            inputLayoutDescrip.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_name:
                    validateName();
                    break;
                case R.id.input_phone:
                    validatePhone();
                    break;
                case R.id.input_email:
                    validateEmail();
                    break;
                case R.id.input_descrip:
                    validateDescrip();
                    break;
            }
        }
    }

}

