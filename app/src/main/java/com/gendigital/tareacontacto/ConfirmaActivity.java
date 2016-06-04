package com.gendigital.tareacontacto;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ConfirmaActivity extends AppCompatActivity {

    private TextView tvName, tvBirthday, tvPhone, tvEmail, tvDescrip;
    private Button btn_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirma);
        tvName = (TextView) findViewById(R.id.tvName);
        tvBirthday = (TextView) findViewById(R.id.tvBirthday);
        tvPhone = (TextView) findViewById(R.id.tvPhone);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvDescrip = (TextView) findViewById(R.id.tvDescrip);

        Bundle bundle = getIntent().getExtras();
        tvName.setText(bundle.getString("Name"));
        tvPhone.setText(bundle.getString("Phone"));
        tvEmail.setText(bundle.getString("Email"));
        tvDescrip.setText(bundle.getString("Descrip"));
        int bdDay = bundle.getInt("BirthdayDay");
        int bdMonth = bundle.getInt("BirthdayMonth");
        int bdYear = bundle.getInt("BirthdayYear");

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy", Locale.US);
        String formattedBirthday = sdf.format(getDate(bdYear, bdMonth, bdDay));

        tvBirthday.setText(formattedBirthday);

        btn_edit = (Button) findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Regresando...", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
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
}
