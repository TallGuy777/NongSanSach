package com.example.md18_and102_asm.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.md18_and102_asm.R;
import com.example.md18_and102_asm.database.DbHelper;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText edtUserNameLogin;
    TextInputEditText edtPasswordLogin;
    Button btnLogin;
    TextView tvForgotPassword;
    TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Ánh xạ
        edtUserNameLogin = findViewById(R.id.userNameLogin);
        edtPasswordLogin = findViewById(R.id.passwordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvRegister = findViewById(R.id.tvRegister);

        DbHelper dbHelper = new DbHelper(this);
        String tenDangNhap = "admin";
        String matKhau = "123";
        String hoTen = "vuphong";

        // Gọi phương thức register từ DbHelper để thêm tài khoản mới vào cơ sở dữ liệu
        dbHelper.register(tenDangNhap, matKhau, hoTen);

        //Bắt sự kiện click vào tvForgotPassword
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPassword.class));
            }
        });

        //Bắt sự kiện click tvRegister
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        //Bắt sự kiện click button Login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenDangNhap = edtUserNameLogin.getText().toString();
                String matKhau = edtPasswordLogin.getText().toString();
                DbHelper dbHelper = new DbHelper(LoginActivity.this);

                //Kiểm tra việc nhập liệu
                if (tenDangNhap.isEmpty() && matKhau.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Chưa nhập tài khoản và mật khẩu", Toast.LENGTH_SHORT).show();
                } else if (tenDangNhap.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Chưa nhập tài khoản", Toast.LENGTH_SHORT).show();
                } else if (matKhau.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Chưa nhập mật khẩu", Toast.LENGTH_SHORT).show();
                } else {
                    if (tenDangNhap.equals("admin") && matKhau.equals("123")) {
                        // Nếu tên đăng nhập và mật khẩu đúng, chuyển đến MainActivity
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    else {
                        if (dbHelper.login(tenDangNhap,matKhau) == 1){
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("tendangnhap",tenDangNhap);
                            editor.apply();//lưu dữ liệu vào data với key và value
                            startActivity(new Intent(LoginActivity.this,SanPhamUser1Activity.class));
                        }
                         else {
                                Toast.makeText(LoginActivity.this, "Tài khoảng hoặc mật khẩu sai!", Toast.LENGTH_SHORT).show();
                            // Nếu tên đăng nhập hoặc mật khẩu không đúng, hiển thị thông báo lỗi
                        }
                    }
                }
            }
        });
    }
}