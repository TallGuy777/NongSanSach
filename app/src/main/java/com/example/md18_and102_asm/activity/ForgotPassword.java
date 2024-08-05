package com.example.md18_and102_asm.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.md18_and102_asm.R;
import com.example.md18_and102_asm.database.DbHelper;
import com.google.android.material.textfield.TextInputEditText;

public class ForgotPassword extends AppCompatActivity {
    DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //Khai báo biến và ánh xạ
        TextInputEditText usernameForgot = findViewById(R.id.userNameForgot);
        Button btnReset = findViewById(R.id.btnReset);
        
        db = new DbHelper(this);
        
        //Bắt sự kiện click btnReset
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenDangNhap = usernameForgot.getText().toString();
                boolean check = db.checkUsername(tenDangNhap);
                if (check){
                    Intent intent = new Intent(ForgotPassword.this, ResetPassword.class);
                    intent.putExtra("username",tenDangNhap);
                    startActivity(intent);
                }else {
                    Toast.makeText(ForgotPassword.this, "User does not exists...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}