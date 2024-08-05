package com.example.md18_and102_asm.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import android.widget.Toast;

import com.example.md18_and102_asm.R;
import com.example.md18_and102_asm.activity.MainActivity;
import com.example.md18_and102_asm.adapter.SanPhamAdapter;
import com.example.md18_and102_asm.dao.SanPhamDAO;
import com.example.md18_and102_asm.model.SanPham;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class QLSPFragment extends Fragment {
    ImageView icAddSP, imgAdd;
    SanPhamAdapter sanPhamAdapter;
    SanPhamDAO sanPhamDAO;
    ArrayList<SanPham> listSanpham;
    private RecyclerView recyclerView;

    private SearchView searchView;

    public QLSPFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listSanpham = new ArrayList<>();
        sanPhamDAO = new SanPhamDAO(requireContext());
        listSanpham.addAll(sanPhamDAO.getListSanPham());
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_q_l_s_p, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        icAddSP = view.findViewById(R.id.icAddSP);
        imgAdd = view.findViewById(R.id.imgAdd);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));

        sanPhamAdapter = new SanPhamAdapter(requireContext(),listSanpham,sanPhamDAO);
        recyclerView.setAdapter(sanPhamAdapter);

        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                sanPhamAdapter.getFilter().filter(newText);
                return true;
            }
        });

        //Bắt sự kiện cho icAddSP
        icAddSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_add,null);
                builder.setView(view);
                AlertDialog dialog = builder.create();

                //Ánh xạ
                TextInputEditText edtTenSPAdd = view.findViewById(R.id.edtTenSPAdd);
                TextInputEditText edtGiaBanAdd = view.findViewById(R.id.edtGiaBanAdd);
                TextInputEditText edtSoLuongAdd = view.findViewById(R.id.edtSoLuongAdd);

                EditText edtURL = view.findViewById(R.id.edtURL);
                imgAdd = view.findViewById(R.id.imgAdd);
                Button btnInsertImage = view.findViewById(R.id.btnInsertImage);

                //Bắt sự kiện cho btnInsertImage
                btnInsertImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = edtURL.getText().toString();
//                        new FetchImage(url).start();
                        Picasso.get().load(url).into(imgAdd);
                    }
                });

                Button btnAdd = view.findViewById(R.id.btnAdd);

                //Bắt sự kiện cho btnAdd
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tenSP = edtTenSPAdd.getText().toString();
                        String giaBan = edtGiaBanAdd.getText().toString();
                        String soLuong = edtSoLuongAdd.getText().toString();
                        String url = edtURL.getText().toString();

                        //Kiểm tra việc nhập liệu
                        if (tenSP.isEmpty() || giaBan.isEmpty()){
                            Toast.makeText(getContext(), "Please fill All details", Toast.LENGTH_SHORT).show();
                        }else {
                            SanPham sp = new SanPham();
                            sp.setTensp(tenSP);
                            sp.setGiaban(Integer.parseInt(giaBan));
                            sp.setAvatar(url);

                            boolean check = sanPhamDAO.addSanPham(sp);
                            if (check){
                                Toast.makeText(getActivity(), "Successfully Added", Toast.LENGTH_SHORT).show();
                                listSanpham.clear();
                                listSanpham.addAll(sanPhamDAO.getListSanPham());
                                sanPhamAdapter.notifyDataSetChanged();
                                dialog.dismiss();
                            }else {
                                Toast.makeText(getContext(), "Failed Add", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                dialog.show();

                //Bắt sự kiện cho btnCancelAdd
                Button btnCancelAdd = view.findViewById(R.id.btnCancelAdd);
                btnCancelAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
        return view;
    }
}