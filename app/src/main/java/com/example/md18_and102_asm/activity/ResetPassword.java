package com.example.md18_and102_asm.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.md18_and102_asm.R;
import com.example.md18_and102_asm.database.DbHelper;
import com.google.android.material.textfield.TextInputEditText;

public class ResetPassword extends AppCompatActivity {
    DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        //Khai báo biến và ánh xạ
        TextView userNameReset = findViewById(R.id.userNameReset);
        TextInputEditText passwordReset = findViewById(R.id.passwordReset);
        TextInputEditText confirmPasswordReset = findViewById(R.id.confirmPasswordReset);

        //Nhận dữ liệu đã được check từ ForgotActivity
        db = new DbHelper(this);
        Intent intent = getIntent();
        userNameReset.setText(intent.getStringExtra("username"));

        Button btnReset = findViewById(R.id.btnConfirm);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenDangNhapReset = userNameReset.getText().toString();
                String newPassword = passwordReset.getText().toString();
                String newConfirmPassword = confirmPasswordReset.getText().toString();

                //Kiểm tra việc nhập liệu
                if (tenDangNhapReset.isEmpty() || newPassword.isEmpty() || newConfirmPassword.isEmpty()){
                    Toast.makeText(ResetPassword.this, "Please fill All details", Toast.LENGTH_SHORT).show();
                }else {
                    if (newConfirmPassword.compareTo(newPassword) == 0){
                        boolean check = db.updatePassword(tenDangNhapReset,newPassword);
                        if (check){
                            startActivity(new Intent(ResetPassword.this, LoginActivity.class));;
                            Toast.makeText(ResetPassword.this, "Password successfully updated", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(ResetPassword.this, "Password not updated...!!", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(ResetPassword.this, "Confirm Password and Password didn't match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}