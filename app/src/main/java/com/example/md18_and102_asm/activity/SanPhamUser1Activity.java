package com.example.md18_and102_asm.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.md18_and102_asm.R;
import com.example.md18_and102_asm.adapter.SanPhamUser1Adapter;
import com.example.md18_and102_asm.dao.SanPhamDAO;
import com.example.md18_and102_asm.model.SanPham;

import java.util.ArrayList;

public class SanPhamUser1Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SanPhamUser1Adapter sanPhamAdapter;
    private ArrayList<SanPham> sanPhamList = new ArrayList<>();
    private SanPhamDAO sanPhamDAO;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sanphamuser);

        recyclerView = findViewById(R.id.recyclerView);
        searchEditText = findViewById(R.id.searchEditText);

        sanPhamDAO = new SanPhamDAO(this);
        sanPhamList = sanPhamDAO.getListSanPham();
        sanPhamAdapter = new SanPhamUser1Adapter(this, sanPhamList, sanPhamDAO);
        recyclerView.setAdapter(sanPhamAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (sanPhamList.isEmpty()) {
            Toast.makeText(this, "Không có sản phẩm nào.", Toast.LENGTH_SHORT).show();
        }

        // Thiết lập sự kiện cho EditText
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchText = s.toString().toLowerCase().trim();
                ArrayList<SanPham> filteredList = new ArrayList<>();
                for (SanPham sanPham : sanPhamList) {
                    if (sanPham.getTensp().toLowerCase().contains(searchText)) {
                        filteredList.add(sanPham);
                    }
                }
                sanPhamAdapter.setSanPhamList(filteredList);
            }
        });
    }
}