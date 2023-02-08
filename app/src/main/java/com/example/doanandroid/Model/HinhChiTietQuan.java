package com.example.doanandroid.Model;

import java.util.List;

public class HinhChiTietQuan {
    private List<HinhAnhDiaDiem> hinhAnhDiaDiems;

    public HinhChiTietQuan(List<HinhAnhDiaDiem> hinhAnhDiaDiems) {
        this.hinhAnhDiaDiems = hinhAnhDiaDiems;
    }

    public List<HinhAnhDiaDiem> getHinhAnhDiaDiems() {
        return hinhAnhDiaDiems;
    }

    public void setHinhAnhDiaDiems(List<HinhAnhDiaDiem> hinhAnhDiaDiems) {
        this.hinhAnhDiaDiems = hinhAnhDiaDiems;
    }
}
