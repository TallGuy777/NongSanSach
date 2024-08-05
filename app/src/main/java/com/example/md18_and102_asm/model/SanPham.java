package com.example.md18_and102_asm.model;

import android.graphics.Bitmap;

public class SanPham {
    private int masp;
    private String tensp;
    private int giaban;
    private int soluong;
    private String avatar;
    private double totalPrice;

    //Constructor
    public SanPham(int masp, String tensp, int giaban, int soluong, String avatar) {
        this.masp = masp;
        this.tensp = tensp;
        this.giaban = giaban;
        this.soluong = soluong;
        this.avatar = avatar;
        this.totalPrice = giaban * soluong;
    }

    public SanPham() {
    }

    public int getMasp() {
        return masp;
    }

    public void setMasp(int masp) {
        this.masp = masp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public int getGiaban() {
        return giaban;
    }

    public void setGiaban(int giaban) {
        this.giaban = giaban;
    }

    public int getSoluong() {return soluong;}

    public void setSoluong(int soluong) {
        this.soluong = soluong;
        this.totalPrice = giaban * soluong;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
