package com.example.md18_and102_asm.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.LayoutTransition;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.md18_and102_asm.R;
import com.example.md18_and102_asm.adapter.SanPhamAdapter;
import com.example.md18_and102_asm.dao.SanPhamDAO;
import com.example.md18_and102_asm.databinding.ActivityMainBinding;
import com.example.md18_and102_asm.fragment.GioiThieuFragment;
import com.example.md18_and102_asm.fragment.QLSPFragment;
import com.example.md18_and102_asm.fragment.SettingFragment;
import com.example.md18_and102_asm.model.SanPham;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Handler mainHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        QLSPFragment qlspFragment = new QLSPFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer,qlspFragment);
        fragmentTransaction.commit();
        //Khai báo biến và ánh xạ
        NavigationView navigationView = findViewById(R.id.navigationView);
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        Toolbar toolbar = findViewById(R.id.toolBar);

        //Thiết lập thanh công cụ là thanh ứng dụng chính của activity
        setSupportActionBar(toolbar);
        //Cho phép hiển thị nút back trên thanh công cụ
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Setup DrawerToggle (dùng để thay đổi hình ảnh của nút toggle trên thanh toolbar
        //khi người dùng mở và đóng thanh điều hướng (navigation drawer)
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                MainActivity.this,
                drawerLayout,
                toolbar,
                R.string.open,
                R.string.close);
        //bật chức năng hiển thị hình ảnh của DrawerToggle trên toolbar.
        //Khi chức năng này được bật, hình ảnh mặc định của DrawerToggle sẽ hiển thị trên toolbar
        //Điều này cho phép người dùng nhận biết được sự mở và đóng của navigation drawer thông
        //qua hình ảnh
        drawerToggle.setDrawerIndicatorEnabled(true);

        //đồng bộ trạng thái của DrawerToggle với trạng thái của DrawerLayout
        drawerToggle.syncState();

        //gắn kết DrawerToggle với DrawerLayout
        drawerLayout.addDrawerListener(drawerToggle);

        //Setup sự kiện click vào item tương ứng trong NavigationView
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.logOut){
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    Toast.makeText(MainActivity.this, "Logout Successfull", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.qlsp) {
                    toolbar.setTitle("Quản lý sản phẩm");
                    QLSPFragment qlspFragment = new QLSPFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer,qlspFragment);
                    fragmentTransaction.commit();
                    drawerLayout.close();
                }else {
                    toolbar.setTitle("Setting");
                    SettingFragment settingFragment = new SettingFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer,settingFragment);
                    fragmentTransaction.commit();
                    drawerLayout.close();
                }
                return true;
            }
        });
    }
}