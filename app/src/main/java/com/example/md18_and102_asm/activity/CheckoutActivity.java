package com.example.md18_and102_asm.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.md18_and102_asm.R;
import android.content.DialogInterface;


public class CheckoutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout);

        TextView txtTongTien = findViewById(R.id.tongtien);
        // Nhận dữ liệu tổng tiền từ Intent
        Intent intent = getIntent();
        if (intent != null) {
            double tongTien = intent.getDoubleExtra("tongtien", 0.0);
            txtTongTien.setText(String.valueOf(tongTien));
        }
        Button checkoutButton = findViewById(R.id.checkout_button);
        RadioButton paymentCODRadioButton = findViewById(R.id.payment_cod);

        // Trong phương thức onClick của nút "Đặt hàng"
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kiểm tra xem RadioButton "Thanh toán khi đặt hàng" có được chọn không
                if (paymentCODRadioButton.isChecked()) {
                    // Nếu RadioButton được chọn, hiển thị thông báo và thực hiện đặt hàng
                    AlertDialog.Builder builder = new AlertDialog.Builder(CheckoutActivity.this);
                    builder.setTitle("Đặt hàng thành công, đơn hàng đang được giao đến bạn");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Thực hiện các hành động cần thiết sau khi nhấn nút OK
                        }
                    });
                    builder.show();
                } else {
                    // Nếu RadioButton không được chọn, hiển thị thông báo không thành công
                    AlertDialog.Builder builder = new AlertDialog.Builder(CheckoutActivity.this);
                    builder.setTitle("Nhà hàng chưa hỗ trợ thành toán qua phương thức này");
                    builder.setMessage("Vui lòng chọn phương thức thanh toán khác.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Thực hiện các hành động cần thiết sau khi nhấn nút OK
                        }
                    });
                    builder.show();
                }
            }
        });
}
}