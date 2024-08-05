package com.example.md18_and102_asm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.md18_and102_asm.R;
import com.example.md18_and102_asm.dao.SanPhamDAO;
import com.example.md18_and102_asm.adapter.SanPhamUserAdapter;
import com.example.md18_and102_asm.model.SanPham;

import java.util.ArrayList;

public class SanPhamUserActivity extends AppCompatActivity {
    TextView tvdangNhap;
    TextView tvdangKy;
    private RecyclerView recyclerView;
    private SanPhamUserAdapter sanPhamAdapter;
    private ArrayList<SanPham> sanPhamList = new ArrayList<>();
    private SanPhamDAO sanPhamDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sanphamusernologin);

        tvdangNhap = findViewById(R.id.tvdangNhap);
        tvdangKy = findViewById(R.id.tvdangKy);

        tvdangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SanPhamUserActivity.this, LoginActivity.class));
            }
        });
        tvdangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SanPhamUserActivity.this, RegisterActivity.class));
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo adapter mới
        sanPhamDAO = new SanPhamDAO(this);
        sanPhamList = sanPhamDAO.getListSanPham();
        sanPhamAdapter = new SanPhamUserAdapter(this, sanPhamList, sanPhamDAO);
        recyclerView.setAdapter(sanPhamAdapter);

        if (!sanPhamList.isEmpty()) {
            sanPhamAdapter.setSanPhamList(sanPhamList);
            sanPhamAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Không có sản phẩm nào.", Toast.LENGTH_SHORT).show();
        }
    }
}