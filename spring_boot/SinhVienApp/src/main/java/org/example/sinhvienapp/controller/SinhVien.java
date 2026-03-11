package org.example.sinhvienapp.controller;

public class SinhVien {
  String mssv, name, namSinh, gioiTinh;

  public SinhVien(String mssv, String name, String namSinh, String gioiTinh) {
    this.mssv = mssv;
    this.name = name;
    this.namSinh = namSinh;
    this.gioiTinh = gioiTinh;
  }

  public String getMssv() {
    return mssv;
  }

  public void setMssv(String mssv) {
    this.mssv = mssv;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getNamSinh() {
    return namSinh;
  }

  public void setNamSinh(String namSinh) {
    this.namSinh = namSinh;
  }

  public String getGioiTinh() {
    return gioiTinh;
  }

  public void setGioiTinh(String gioiTinh) {
    this.gioiTinh = gioiTinh;
  }

}
