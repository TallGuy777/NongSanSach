package com.example.md18_and102_asm.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "QLSP";
    private static final int DATABASE_VERSION = 5;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String qND = "CREATE TABLE NguoiDung(" +
                "tendangnhap TEXT PRIMARY KEY, " +
                "matkhau TEXT, " +
                "hoten TEXT)";
        db.execSQL(qND);

        String qSP = "CREATE TABLE Products(" +
                "masp INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tensp TEXT, " +
                "giaban INTEGER, " +
                "soluong INTEGER, " +
                "avatar TEXT)";
        db.execSQL(qSP);

        String qHD = "CREATE TABLE HOADON(" +
                "mahd INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tongtien INTEGER, " +
                "status BOOLEAN, " +
                "create_at TIMESTAMP, " +
                "nguoidung_id INTEGER, " +
                "FOREIGN KEY(nguoidung_id) REFERENCES NguoiDung(tendangnhap))";
        db.execSQL(qHD);

        String qHDCT = "CREATE TABLE HOADONCHITIET(" +
                "soluong INTEGER, " +
                "tongtientungsp FLOAT, " +
                "sanpham_id INTEGER, " +
                "hoadon_id INTEGER, " +
                "FOREIGN KEY(sanpham_id) REFERENCES Products(masp), " +
                "FOREIGN KEY(hoadon_id) REFERENCES HOADON(mahd))";
        db.execSQL(qHDCT);

        // Nạp dữ liệu cho bảng Products
        insertInitialProducts(db);
    }

    private void insertInitialProducts(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        values.put("tensp", "Trà Chang");
        values.put("giaban", 10000);
        values.put("soluong", 50);
        values.put("avatar", "https://www.unileverfoodsolutions.com.vn/dam/global-ufs/mcos/phvn/vietnam/calcmenu/recipes/VN-recipes/other/energizing-lemon-tea/main-header.jpg");
        db.insert("Products", null, values);

        values.put("tensp", "Sting");
        values.put("giaban", 20000);
        values.put("soluong", 30);
        values.put("avatar", "https://hongphatfood.com/wp-content/uploads/2020/02/Sting-strawberry-drink-soft1.jpg");
        db.insert("Products", null, values);

        values.put("tensp", "Coca Cola");
        values.put("giaban", 15000);
        values.put("soluong", 20);
        values.put("avatar", "https://tea-3.lozi.vn/v1/ship/resized/losupply-thanh-hoa-thanh-pho-thanh-hoa-thanh-hoa-1648799628125182440-nuoc-ngot-coca-cola-lon-320ml-0-1659606470?w=480&type=o");
        db.insert("Products", null, values);

        // Thêm dữ liệu khác nếu cần
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS NguoiDung");
            db.execSQL("DROP TABLE IF EXISTS Products");
            db.execSQL("DROP TABLE IF EXISTS HOADON");
            db.execSQL("DROP TABLE IF EXISTS HOADONCHITIET");
            onCreate(db);
        }
    }

    // Hàm register
    public void register(String tenDangNhap, String matKhau, String hoTen) {
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("tendangnhap", tenDangNhap);
            cv.put("matkhau", matKhau);
            cv.put("hoten", hoTen);
            db.insert("NguoiDung", null, cv);
        } catch (Exception e) {
            Log.e("DbHelper", "Lỗi khi đăng ký người dùng", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    // Hàm login
    public int login(String tenDangNhap, String matKhau) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        int result = 0;
        try {
            db = getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM NguoiDung WHERE tendangnhap=? AND matkhau=?", new String[]{tenDangNhap, matKhau});
            if (cursor.moveToFirst()) {
                result = 1;
            }
        } catch (Exception e) {
            Log.e("DbHelper", "Lỗi khi đăng nhập", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return result;
    }

    // Hàm checkUsername
    public boolean checkUsername(String tenDangNhap) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        boolean exists = false;
        try {
            db = getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM NguoiDung WHERE tendangnhap=?", new String[]{tenDangNhap});
            exists = cursor.getCount() > 0;
        } catch (Exception e) {
            Log.e("DbHelper", "Lỗi khi kiểm tra tên đăng nhập", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return exists;
    }

    // Hàm updatePassword
    public boolean updatePassword(String tenDangNhap, String password) {
        SQLiteDatabase db = null;
        int result = -1;
        try {
            db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("matkhau", password);
            result = db.update("NguoiDung", contentValues, "tendangnhap=?", new String[]{tenDangNhap});
        } catch (Exception e) {
            Log.e("DbHelper", "Lỗi khi cập nhật mật khẩu", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return result != -1;
    }
}
