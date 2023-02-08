package com.example.doanandroid.Model;

public class Flag {
    String imgAvt, txtNameCMT,txtContent;

    public Flag(String imgAvt, String txtNameCMT, String txtContent) {
        this.imgAvt = imgAvt;
        this.txtNameCMT = txtNameCMT;
        this.txtContent = txtContent;
    }

    public String getImgAvt() {
        return imgAvt;
    }

    public void setImgAvt(String imgAvt) {
        this.imgAvt = imgAvt;
    }

    public String getTxtNameCMT() {
        return txtNameCMT;
    }

    public void setTxtNameCMT(String txtNameCMT) {
        this.txtNameCMT = txtNameCMT;
    }

    public String getTxtContent() {
        return txtContent;
    }

    public void setTxtContent(String txtContent) {
        this.txtContent = txtContent;
    }
}
