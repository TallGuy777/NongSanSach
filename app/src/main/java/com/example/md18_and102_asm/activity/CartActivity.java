package com.example.md18_and102_asm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.md18_and102_asm.R;
import com.squareup.picasso.Picasso;

public class CartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                double tongTien = bundle.getDouble("tongtien", 0.0);
                String tensp = bundle.getString("tensp", "");
                double giasp = bundle.getDouble("giasp", 0.0);
                int soluong = bundle.getInt("soluong", 0);
                String hinhanh = bundle.getString("hinhanh", "");

                // Hiển thị thông tin sản phẩm trên giao diện
                TextView txttensp = findViewById(R.id.txttensp);
                TextView txtgiasp = findViewById(R.id.txtgiasp);
                TextView txtsoluong = findViewById(R.id.txtsoluong);
                TextView txttongtien = findViewById(R.id.txttongtien);
                ImageView imghinhanh = findViewById(R.id.imghinhanh);

                txttensp.setText(tensp);
                txtgiasp.setText(String.valueOf(giasp));
                txtsoluong.setText(String.valueOf(soluong));
                txttongtien.setText(String.valueOf(tongTien));
                Picasso.get().load(hinhanh).into(imghinhanh);

                // Xử lý sự kiện khi nhấn nút "Thanh toán"
                Button btnThanhToan = findViewById(R.id.btnthanhtoan);
                btnThanhToan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Tạo Intent để chuyển đến CheckoutActivity và đính kèm dữ liệu tổng tiền
                        Intent checkoutIntent = new Intent(CartActivity.this, CheckoutActivity.class);
                        checkoutIntent.putExtra("tongtien", tongTien);
                        startActivity(checkoutIntent);
                    }
                });
            }
        }
    }
}