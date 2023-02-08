package com.example.doanandroid.Model;


import java.io.Serializable;

public class Location implements Serializable {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImgUser() {
        return imgUser;
    }

    public void setImgUser(String imgUser) {
        this.imgUser = imgUser;
    }

    public String getTxtName() {
        return txtName;
    }

    public void setTxtName(String txtName) {
        this.txtName = txtName;
    }

    public String getTxtUser() {
        return txtUser;
    }

    public void setTxtUser(String txtUser) {
        this.txtUser = txtUser;
    }

    String id;
    String img;
    String imgUser;
    String txtName,txtUser;


    public Location(String id, String img, String imgUser, String txtName, String txtUser) {
        this.id = id;
        this.img = img;
        this.imgUser = imgUser;
        this.txtName = txtName;
        this.txtUser = txtUser;
    }
}