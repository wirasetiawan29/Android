package com.logbook.wira.switchactivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by wira on 1/7/16.
 */
public class halaman_kedua extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.halaman_kedua);

        TextView txtNama = (TextView) findViewById(R.id.txtNama);
        TextView txtEmail = (TextView) findViewById(R.id.txtEmail);
        Button tombolTutup = (Button) findViewById(R.id.tombolTutup);

        Intent i = getIntent();

        //Mengambil Data
        String nama = i.getStringExtra("nama");
        String email = i.getStringExtra("email");
        Log.e("Halaman Kedua", nama + "." + email);

        //Menampilkan
        txtNama.setText(nama);
        txtEmail.setText(email);

        tombolTutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });

    }
}
