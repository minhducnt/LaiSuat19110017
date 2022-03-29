package com.android.s19110017;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class ResultActivity extends AppCompatActivity {
    TextView t1, t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        t1 = (TextView) findViewById(R.id.tienlai);

        t2 = (TextView) findViewById(R.id.tongtien);
        Intent intent = getIntent();
        double a = intent.getDoubleExtra("tonglai", 0.0);
        String lai = Double.toString(a);
        double b = intent.getDoubleExtra("sotiengui", 0.0);
        String tongtien = Double.toString(b);
        t1.setText(lai);
        t2.setText(tongtien);
    }

    public void Camera(View view) throws IOException {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        int REQUEST_ID_IMAGE_CAPTURE = 100;
        // Start camera and wait for the results.
        this.startActivityForResult(intent, REQUEST_ID_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) == null) {
            Intent intent2 = new Intent(ResultActivity.this, MainActivity.class);
            startActivity(intent2);
        }
    }
}
