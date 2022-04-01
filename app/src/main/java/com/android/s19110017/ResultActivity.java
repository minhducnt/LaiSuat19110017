package com.android.s19110017;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class ResultActivity extends AppCompatActivity {
    //Khai báo biến
    TextView t1, t2;
    Button btnTake, btnBack;
    ImageView imgView;

    //Lấy dữ liệu lại từ bên Result
    ActivityResultLauncher<Intent> activityCamera = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent intent = result.getData();
                    if (intent != null) {
                        Bitmap bitmap = (Bitmap) intent.getExtras().get("data");//"Data" là mặc định
                        imgView.setImageBitmap(bitmap);
                        Intent intentX = new Intent(ResultActivity.this, MainActivity.class);
                        startActivity(intentX);
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        addControl();
        addEvent();
        processData();
    }

    void addControl() {
        t1 = (TextView) findViewById(R.id.tienlai);
        t2 = (TextView) findViewById(R.id.tongtien);
        btnTake = (Button) findViewById(R.id.btn_result);
        btnBack = (Button) findViewById(R.id.btn_back);
        imgView = (ImageView) findViewById(R.id.imageView);
    }

    void addEvent() {
        btnTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                activityCamera.launch(intent);
            }
        });

        //Đưa dữ liệu lại MainActivity
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                Bundle bundle = intent.getBundleExtra("dulieubundle");
                int tienGui = bundle.getInt("tienGui", 0);
                int laiSuat = bundle.getInt("laiSuat", 0);
                int kyHan = bundle.getInt("kyHan", 0);

                Intent intentback = new Intent();
                intentback.putExtra("tienGui", tienGui);
                intentback.putExtra("laiVaGoc", laiSuat);
                intentback.putExtra("kyHan", kyHan);
                setResult(RESULT_OK, intentback);
                finish();
            }
        });
    }

    void processData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("dulieubundle");
        if (bundle != null) {
            DecimalFormat formatnumber = new DecimalFormat("#,###,###,###");
            int laiThucTe = bundle.getInt("tienlai", 0);
            int laiVaGoc = bundle.getInt("tongtiennhan", 0);
            t1.setText(formatnumber.format(laiThucTe) + " đ");
            t2.setText(formatnumber.format(laiVaGoc) + " đ");
        }
    }
}
