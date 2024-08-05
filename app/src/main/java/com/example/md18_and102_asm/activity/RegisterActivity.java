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

public class RegisterActivity extends AppCompatActivity {
    TextInputEditText edtUserNameRegister;
    TextInputEditText edtFullNameRegister;
    TextInputEditText edtPasswordRegister;
    TextInputEditText edtConfirmPasswordRegister;
    Button btnRegister;
    TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Ánh xạ
        edtUserNameRegister = findViewById(R.id.userNameRegister);
        edtFullNameRegister = findViewById(R.id.fullNameRegister);
        edtPasswordRegister = findViewById(R.id.passwordRegister);
        edtConfirmPasswordRegister = findViewById(R.id.confirmPasswordRegister);
        btnRegister = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLogin);

        //Bắt sự kiện click vào tvLogin
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });

        //Bắt sự kiện click button Register
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenDangNhap = edtUserNameRegister.getText().toString();
                String hoTen = edtFullNameRegister.getText().toString();
                String matKhau = edtPasswordRegister.getText().toString();
                String confirmPassword = edtConfirmPasswordRegister.getText().toString();

                DbHelper dbHelper = new DbHelper(RegisterActivity.this);
                //Kiểm tra việc nhập liệu
                boolean isValidInput = true; // Khởi tạo biến boolean để kiểm tra hợp lệ của dữ liệu nhập vào

// Kiểm tra xem có trường nào bị trống không
                if (tenDangNhap.isEmpty() || hoTen.isEmpty() || matKhau.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    isValidInput = false; // Gán giá trị false cho biến isValidInput nếu có trường nào bị trống
                } else {
                    // Kiểm tra định dạng của email

                    // Kiểm tra xem tài khoản đã tồn tại chưa
                    boolean isUsernameExist = dbHelper.checkUsername(tenDangNhap);
                    if (isUsernameExist) {
                        edtUserNameRegister.setError("Tài khoản đã được sử dụng");
                        isValidInput = false; // Gán giá trị false cho biến isValidInput nếu tài khoản đã tồn tại
                    }

                    // Kiểm tra xác nhận mật khẩu
                    if (!confirmPassword.equals(matKhau)) {
                        Toast.makeText(RegisterActivity.this, "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
                        isValidInput = false; // Gán giá trị false cho biến isValidInput nếu mật khẩu xác nhận không khớp
                    }

                    // Kiểm tra độ mạnh của mật khẩu
                    if (!isValid(matKhau)) {
                        Toast.makeText(RegisterActivity.this, "Mật khẩu phải ít nhất 8 ký tự, bao gồm chữ cái, chữ số và ký tự đặc biệt", Toast.LENGTH_SHORT).show();
                        isValidInput = false; // Gán giá trị false cho biến isValidInput nếu mật khẩu không hợp lệ
                    }
                }

// Nếu tất cả điều kiện đều hợp lệ, thực hiện đăng ký
                if (isValidInput) {
                    dbHelper.register(tenDangNhap, matKhau, hoTen);
                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

                }
            }
        });
    }

    //Hàm kiểm tra tính hợp lệ của password
    private static boolean isValid(String passwordHere) {
        int flag1 = 0, flag2 = 0, flag3 = 0;
        if (passwordHere.length() < 0){
            return false;
        }else {
            for (int p = 0; p < passwordHere.length(); p++){
                //Kiểm tra ký tự hiện tại có phải là một chữ cái hay không
                if (Character.isLetter(passwordHere.charAt(p))){
                    flag1 = 1;
                }
            }
            for (int r = 0; r < passwordHere.length(); r++){
                //Kiểm tra ký tự hiện tại có phải là một chữ số hay không
                if (Character.isDigit(passwordHere.charAt(r))){
                    flag2 = 1;
                }
            }
            for (int s = 0; s < passwordHere.length(); s++){
                //lấy ký tự hiện tại
                char c = passwordHere.charAt(s);
                //--và kiểm tra xem nó có thuộc các ký tự đặc biệt như dấu chấm câu
                //(33-46) hoặc ký tự '@' (64) hay không
                if (c >= 33 && c <= 46 || c == 64){
                    flag3 = 1;
                }
            }
            if (flag1 == 1 && flag2 == 1 && flag3 == 1){
                return true;
            }else {
                return false;
            }
        }
    }
}