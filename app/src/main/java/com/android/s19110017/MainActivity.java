package com.android.s19110017;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    //Khai báo biến
    EditText e1, e2, e3;
    Button btnClick;

    //Lấy dữ liệu lại từ bên Result
    ActivityResultLauncher<Intent> activityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        if (intent != null) {
                            int tienGui = intent.getIntExtra("tienGui", 0);
                            int laiSuat = intent.getIntExtra("laiVaGoc", 0);
                            int kyHan = intent.getIntExtra("kyHan", 0);
                            e1.setText(tienGui + "");
                            e2.setText(laiSuat + "");
                            e3.setText(kyHan + "");
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControl();
        addEvent();
    }

    void addControl() {
        e1 = findViewById(R.id.tiengui);
        e2 = findViewById(R.id.laisuat);
        e3 = findViewById(R.id.kyhan);
        btnClick = findViewById(R.id.btn_result);
    }

    void addEvent() {
        e1.addTextChangedListener(onTextChangedListener());
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                int tienGui = Integer.parseInt(e1.getText().toString().replaceAll(",", ""));
                int laiSuat = Integer.parseInt(e2.getText().toString());
                int kyHan = Integer.parseInt(e3.getText().toString());
                int result = (tienGui * laiSuat * kyHan / 12) / 100;
                Bundle bundle = new Bundle();
                bundle.putInt("tienGui", tienGui);
                bundle.putInt("laiSuat", laiSuat);
                bundle.putInt("kyHan", kyHan);
                bundle.putInt("tienlai", result);
                bundle.putInt("tongtiennhan", result + tienGui);
                intent.putExtra("dulieubundle", bundle);
                activityLauncher.launch(intent);
            }
        });
    }

    private TextWatcher onTextChangedListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                e1.removeTextChangedListener(this);
                try {
                    String originalString = s.toString();
                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);
                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);
                    //Cấu hình text sau khi format thành EditText
                    e1.setText(formattedString);
                    e1.setSelection(e1.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }
                e1.addTextChangedListener(this);
            }
        };
    }
}