package com.example.md18_and102_asm.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.md18_and102_asm.database.DbHelper;
import com.example.md18_and102_asm.model.SanPham;

import java.util.ArrayList;

public class SanPhamDAO {
    private final DbHelper dbHelper;

    public SanPhamDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    // Hàm lấy danh sách sản phẩm
    public ArrayList<SanPham> getListSanPham(){
        ArrayList<SanPham> list = new ArrayList<>();
        SQLiteDatabase database = null;
        Cursor cursor = null;
        try {
            database = dbHelper.getReadableDatabase();
            cursor = database.rawQuery("SELECT * FROM Products", null);
            if (cursor.moveToFirst()) {
                do {
                    list.add(new SanPham(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getInt(2),
                            cursor.getInt(3),
                            cursor.getString(4)));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("SanPhamDAO", "Lỗi khi lấy danh sách sản phẩm", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null) {
                database.close();
            }
        }
        return list;
    }

    // Hàm thêm sản phẩm
    public boolean addSanPham(SanPham sanPham){
        SQLiteDatabase database = null;
        long result = -1;
        try {
            database = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("tensp", sanPham.getTensp());
            values.put("giaban", sanPham.getGiaban());
            values.put("soluong", sanPham.getSoluong());
            values.put("avatar", sanPham.getAvatar());
            result = database.insert("Products", null, values);
        } catch (Exception e) {
            Log.e("SanPhamDAO", "Lỗi khi thêm sản phẩm", e);
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return result != -1;
    }

    // Hàm xóa sản phẩm
    public boolean deleteSanPham(int maSP){
        SQLiteDatabase database = null;
        int result = -1;
        try {
            database = dbHelper.getWritableDatabase();
            result = database.delete("Products", "masp=?", new String[]{String.valueOf(maSP)});
        } catch (Exception e) {
            Log.e("SanPhamDAO", "Lỗi khi xóa sản phẩm", e);
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return result != -1;
    }

    // Hàm cập nhật sản phẩm
    public boolean updateSanPham(SanPham sanPham){
        SQLiteDatabase database = null;
        int result = -1;
        try {
            database = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("tensp", sanPham.getTensp());
            values.put("giaban", sanPham.getGiaban());
            values.put("soluong", sanPham.getSoluong());
            values.put("avatar", sanPham.getAvatar());
            result = database.update("Products", values, "masp=?", new String[]{String.valueOf(sanPham.getMasp())});
        } catch (Exception e) {
            Log.e("SanPhamDAO", "Lỗi khi cập nhật sản phẩm", e);
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return result != -1;
    }
}
