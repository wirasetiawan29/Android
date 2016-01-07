package com.logbook.wira.switchactivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by wira on 1/7/16.
 */
public class halaman_satu  extends Activity {
    EditText inputNama;
    EditText inputEmail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.halaman_satu);

        inputNama = (EditText) findViewById(R.id.nama);
        inputEmail = (EditText) findViewById(R.id.email);
        Button btnKedua = (Button) findViewById(R.id.btnKedua);

        btnKedua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent halaman_selanjutnya = new Intent(getApplicationContext(), halaman_kedua.class);

                halaman_selanjutnya.putExtra("nama", inputNama.getText().toString());
                halaman_selanjutnya.putExtra("email", inputEmail.getText().toString());

                Log.e("n", inputNama.getText()+ "." + inputEmail.getText());

                startActivity(halaman_selanjutnya);
            }
        });


    }

}
